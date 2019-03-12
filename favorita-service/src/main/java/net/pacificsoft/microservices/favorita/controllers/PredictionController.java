package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.LocationNames;
import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.models.Probabilities;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import net.pacificsoft.microservices.favorita.repository.ProbabilitiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity getAllProbabilities() {
        try{
            List<Prediction> predictions = predictionsRepository.findAll();

            return new ResponseEntity(predictions, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/prediction/{probabilityID}/{LocationNameID}")
    public ResponseEntity createCiudad(@PathVariable(value = "probabilityID") Long probabilityId,
                                       @PathVariable(value = "LocationNameID") Long locationNameId,
                                       @Valid @RequestBody Prediction prediction) {
        try{
            if(probabilitiesRepository.exists(probabilityId) && locationNamesRepository.exists(locationNameId)){
                Probabilities probability = probabilitiesRepository.findOne(probabilityId);
                LocationNames locationName = locationNamesRepository.findOne(locationNameId);
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
    public ResponseEntity updateCiudad(
            @PathVariable(value = "id") Long predictionID,
            @Valid @RequestBody Prediction predictionDetails){
        try{
            if(predictionsRepository.exists(predictionID)) {
                Prediction prediction = predictionsRepository.findOne(predictionID);
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
        if(predictionsRepository.exists(predictionID)){
            Prediction prediction = predictionsRepository.findOne(predictionID);
            predictionsRepository.delete(predictionID);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Prediction #" + predictionID +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }

    }


}

