package net.pacificsoft.springbootcrudrest.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import net.pacificsoft.springbootcrudrest.model.LocationNames;
import net.pacificsoft.springbootcrudrest.model.Message;
import net.pacificsoft.springbootcrudrest.repository.LocationNamesRepository;
import net.pacificsoft.springbootcrudrest.repository.MessageRepository;
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

import net.pacificsoft.springbootcrudrest.model.Prediction;
import net.pacificsoft.springbootcrudrest.model.Probabilities;
import net.pacificsoft.springbootcrudrest.repository.PredictionsRepository;
import net.pacificsoft.springbootcrudrest.repository.LocationNamesRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LocationNamesController {
    @Autowired
    private LocationNamesRepository locationNamesRepository;
    @Autowired
    private PredictionsRepository predictionsRepository;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/locationNames")
    public ResponseEntity getAllProbabilities() {
        try{

            List<LocationNames> locationNames = locationNamesRepository.findAll();

            return new ResponseEntity(locationNames, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/locationNames")
    public ResponseEntity createFurgon(@Valid @RequestBody LocationNames locationNames) {
        try{
            LocationNames p = locationNamesRepository.save(locationNames);
            return new ResponseEntity(p, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<String>("New Probabilities not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/locationNames/{id}")
    public ResponseEntity updateFurgon(
            @PathVariable(value = "id") Long locationNameId,
            @Valid @RequestBody LocationNames locationNameDetails){
        try{
            if(locationNamesRepository.exists(locationNameId)){
                LocationNames locationName = locationNamesRepository.findOne(locationNameId);
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
    public ResponseEntity deleteLocationName(
            @PathVariable(value = "id") Long locationNameId){
        if(locationNamesRepository.exists(locationNameId)){
            LocationNames locationName = locationNamesRepository.findOne(locationNameId);
            try {
                if(locationName.getPrediction().size() != 0){
                    Set<Prediction> p =locationName.getPrediction();
                    for(Prediction i: p){
                        i.setLocationNames(null);
                    }
                }
                Message m = locationName.getMessage();
                m.setLocationNames(null);
                messageRepository.save(m);
            }

            catch (Exception e){

            }
            locationNamesRepository.delete(locationName);
            return new ResponseEntity(HttpStatus.OK);

        }
        else{
            return new ResponseEntity<String>("LocationName #" + locationNameId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
