package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.LocationNames;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

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
