package net.pacificsoft.microservices.favorita.controllers;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.*;
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

import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import net.pacificsoft.microservices.favorita.repository.ProbabilitiesRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PredictionController {

    @Autowired
    private ProbabilitiesRepository probabilitiesRepository;
    @Autowired
    private PredictionsRepository predictionsRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private LocationNamesRepository locationNamesRepository;

    @GetMapping("/prediction")
    public ResponseEntity getAllPredictions() {
        try{
            return new ResponseEntity(predictionsRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/prediction/{id}")
    public ResponseEntity getPredictionById(
	@PathVariable(value = "id") Long predictionId){
	if(predictionsRepository.existsById(predictionId)){
            Prediction prediction = predictionsRepository.findById(predictionId).get();
            return new ResponseEntity(prediction, HttpStatus.OK);
        }
	else{
            return new ResponseEntity<String>("Prediction #" + predictionId + 
                               " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/prediction/{probabilityID}/{LocationNameID}")
    public ResponseEntity createPrediction(@PathVariable(value = "probabilityID") Long probabilityId,
                                       @PathVariable(value = "LocationNameID") Long locationNameId,
                                       @Valid @RequestBody Prediction prediction) {
        try{
            if(probabilitiesRepository.existsById(probabilityId) && locationNamesRepository.existsById(locationNameId)){
                Probabilities probability = probabilitiesRepository.findById(probabilityId).get();
                LocationNames locationName = locationNamesRepository.findById(locationNameId).get();
                prediction.setLocationNames(locationName);
                prediction.setProbabilities(probability);

                probability.getPrediction().add(prediction);
                locationName.getPrediction().add(prediction);

                probabilitiesRepository.save(probability);
                locationNamesRepository.save(locationName);
                Prediction posted = predictionsRepository.save(prediction);

                return new ResponseEntity(posted, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<String>("Probability #" + probabilityId + "or LocationName #" +locationNameId +
                        " does not exist, it's not possible create new Prediction", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Prediction", HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/prediction/{id}")
    public ResponseEntity updatePrediction(
            @PathVariable(value = "id") Long predictionID,
            @Valid @RequestBody Prediction predictionDetails){
        try{
            if(predictionsRepository.existsById(predictionID)) {
                Prediction prediction = predictionsRepository.findById(predictionID).get();
                prediction.setName(predictionDetails.getName());
                final Prediction updated = predictionsRepository.save(prediction);
                return new ResponseEntity(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("Prediction #" + predictionID +
                        " does not exist.", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible update "
                    + "Prediction #" + predictionID, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/prediction/{id}")
    public ResponseEntity deletePrediction(
            @PathVariable(value = "id") Long predictionID){
        if(predictionsRepository.existsById(predictionID)){
            Prediction prediction = predictionsRepository.findById(predictionID).get();
            predictionsRepository.delete(prediction);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Prediction #" + predictionID +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }

    }


}

