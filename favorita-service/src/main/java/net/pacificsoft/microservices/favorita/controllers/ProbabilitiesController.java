package net.pacificsoft.microservices.favorita.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;
import net.pacificsoft.microservices.favorita.models.LocationNames;

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
import net.pacificsoft.microservices.favorita.models.Probabilities;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import net.pacificsoft.microservices.favorita.repository.ProbabilitiesRepository;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class ProbabilitiesController {
    
	@Autowired
	private ProbabilitiesRepository probabilitiesRepository;
    @Autowired
	private PredictionsRepository predictionsRepository;  
    
    @GetMapping("/probability")
	public ResponseEntity getAllProbabilities() {
        try{
                return new ResponseEntity(probabilitiesRepository.findAll(), HttpStatus.OK);
        }
		catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                HttpStatus.NOT_FOUND);
        }  
    }
        
    @GetMapping("/probability/{id}")
    public ResponseEntity getProbabilitiesById(
            @PathVariable(value = "id") Long ProbabilitiesId){
        if(probabilitiesRepository.existsById(ProbabilitiesId)){
            Probabilities a = probabilitiesRepository.findById(ProbabilitiesId).get();
            return new ResponseEntity(a, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Probabilities #" + ProbabilitiesId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/probability/{predictionId}")
	public ResponseEntity createProbabilities(@Valid @RequestBody Probabilities probability,
                                        @PathVariable(value = "predictionId") Long predictionId) {
                try{
                    if(predictionsRepository.existsById(predictionId)){
                        Prediction pre = predictionsRepository.findById(predictionId).get();
                        pre.getProbabilitieses().add(probability);
                        probability.setPrediction(pre);
                        Probabilities p = probabilitiesRepository.save(probability);
                        predictionsRepository.save(pre); 
                        return new ResponseEntity(p, HttpStatus.CREATED);
                    }
                    else{
                        return new ResponseEntity<String>("New Probabilities not created.",
                            HttpStatus.NOT_FOUND);
                    }
                }
		catch(Exception e){
                    return new ResponseEntity<String>("New Probabilities not created.",
                            HttpStatus.NOT_FOUND);
                }
    }
    
    @PutMapping("/probability/{id}")
	public ResponseEntity updateProbabilities(
			@PathVariable(value = "id") Long probabilityId,
			@Valid @RequestBody Probabilities probabilityDetails){
            try{
                if(probabilitiesRepository.existsById(probabilityId)){
                    Probabilities probability = probabilitiesRepository.findById(probabilityId).get();
                    probability.setNameID(probabilityDetails.getNameID());
                    probability.setProbability(probabilityDetails.getProbability());
                    final Probabilities updatedpProbability = probabilitiesRepository.save(probability);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Probabilities #" + probabilityId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Probabilities #" + probabilityId, HttpStatus.NOT_FOUND);
            }
	}
    
    @DeleteMapping("/probability/{id}")
	public ResponseEntity deleteProbabilities(
			@PathVariable(value = "id") Long probabilityId){
                if(probabilitiesRepository.existsById(probabilityId)){
                    Probabilities probability = probabilitiesRepository.findById(probabilityId).get();
                    probability.getPrediction().getProbabilitieses().remove(probability);
                    predictionsRepository.save(probability.getPrediction());
                    probabilitiesRepository.delete(probability);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Furgon #" + probabilityId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}