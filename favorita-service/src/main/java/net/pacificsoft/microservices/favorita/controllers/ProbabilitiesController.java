package net.pacificsoft.springbootcrudrest.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

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
import net.pacificsoft.springbootcrudrest.repository.ProbabilitiesRepository;

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
            List<Map<String, Object>> result = new ArrayList();
            List<Probabilities> probabilities = probabilitiesRepository.findAll();
            JSONObject json;
            for(Probabilities p : probabilities){
                json = new JSONObject();
                json.put("id", p.getId());
                json.put("nameId",p.getName());
                json.put("probability", p.getProbability());
                result.add(json.toMap());
            }
                return new ResponseEntity(result, HttpStatus.OK);
        }
		catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                HttpStatus.NOT_FOUND);
        }  
    }

    @PostMapping("/probability")
	public ResponseEntity createFurgon(@Valid @RequestBody Probabilities probability) {
                try{
                    Probabilities p = probabilitiesRepository.save(probability);
                    return new ResponseEntity(p, HttpStatus.CREATED);
                    /*
                    JSONObject json = new JSONObject();
                    Set<Map<String, Object>> ubs = new HashSet();
                    json.put("id", p.getId());
                    json.put("nameId", p.getNameId());
                    json.put("probability", p.getProbability());
                    return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
                    */

                }
		catch(Exception e){
                    return new ResponseEntity<String>("New Probabilities not created.",
                            HttpStatus.NOT_FOUND);
                }
    }
    
    @PutMapping("/probability/{id}")
	public ResponseEntity updateFurgon(
			@PathVariable(value = "id") Long probabilityId,
			@Valid @RequestBody Probabilities probabilityDetails){
            try{
                if(probabilitiesRepository.exists(probabilityId)){
                    Probabilities probability = probabilitiesRepository.findOne(probabilityId);
                    probability.setName(probabilityDetails.getName());
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
	public ResponseEntity deleteFurgon(
			@PathVariable(value = "id") Long probabilityId){
                if(probabilitiesRepository.exists(probabilityId)){
                    Probabilities probability = probabilitiesRepository.findOne(probabilityId);
                    if(probability.getPrediction().size()>0){
                        Set<Prediction> ubFs = probability.getPrediction();
                        for(Prediction r: ubFs){
                            r.setProbabilities(null);
                            predictionsRepository.save(r);
                        }
                    }
                    probabilitiesRepository.delete(probability);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Furgon #" + probabilityId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}