package net.pacificsoft.microservices.favorita.controllers;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.LocationNames;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LocationNamesController {
    @Autowired
    private LocationNamesRepository locationNamesRepository;
    
    @Autowired
    private PredictionsRepository predictionsRepository;

    @GetMapping("/locationNames")
    public ResponseEntity getAllLocationNames() {
        try{
            return new ResponseEntity(locationNamesRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

     @GetMapping("/locationNames/{id}")
    public ResponseEntity getLocationNamesById(
            @PathVariable(value = "id") Long LocationNameId){
        if(locationNamesRepository.existsById(LocationNameId)){
            LocationNames a = locationNamesRepository.findById(LocationNameId).get();
            return new ResponseEntity(a, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Location Names #" + LocationNameId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/locationNames/{predictionId}")
    public ResponseEntity createLocationNames(@Valid @RequestBody LocationNames locationNames,
                        @PathVariable(value = "predictionId") Long predictionId) {
        try{
            if(predictionsRepository.existsById(predictionId)){
                Prediction prediction = predictionsRepository.findById(predictionId).get();
                prediction.getLocationNames().add(locationNames);
                locationNames.setPrediction(prediction);
                LocationNames p = locationNamesRepository.save(locationNames);
                predictionsRepository.save(prediction);
                return new ResponseEntity(p, HttpStatus.CREATED);}
            else{
                return new ResponseEntity<String>("New Location Names not created, prediction not found.",
                    HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("New Location Names not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/locationNames/{id}")
    public ResponseEntity updateLocationNames(
            @PathVariable(value = "id") Long locationNameId,
            @Valid @RequestBody LocationNames locationNameDetails){
        try{
            if(locationNamesRepository.existsById(locationNameId)){
                LocationNames locationName = locationNamesRepository.findById(locationNameId).get();
                locationName.setName(locationNameDetails.getName());
                locationName.setIdname(locationNameDetails.getIdname());
                final LocationNames updateLocationName = locationNamesRepository.save(locationName);
                return new ResponseEntity(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("LocationName #" + locationNameId +
                        " does not exist.", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible update LocationName #" + locationNameId, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/locationNames/{id}")
    public ResponseEntity deleteLocationNames(
            @PathVariable(value = "id") Long locationNameId){
        if(locationNamesRepository.existsById(locationNameId)){
            LocationNames locationName = locationNamesRepository.findById(locationNameId).get();
            locationName.getPrediction().getLocationNames().remove(locationName);
            predictionsRepository.save(locationName.getPrediction());
            locationNamesRepository.delete(locationName);
            return new ResponseEntity(HttpStatus.OK);

        }
        else{
            return new ResponseEntity<String>("LocationName #" + locationNameId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
