package net.pacificsoft.microservices.favorita.controllers;

import javax.validation.Valid;
import net.pacificsoft.microservices.favorita.models.Features;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.repository.FeaturesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class FeatureController {
	
	@Autowired
	private FeaturesRepository featuresRepository;
	@GetMapping("/features")
	public ResponseEntity getAllFeatures() {
		try{
                    return new ResponseEntity(featuresRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/features/{id}")
	public ResponseEntity getFeaturesById(
			@PathVariable(value = "id") Long featureId){
		if(featuresRepository.existsById(featureId)){
                    Features feature = featuresRepository.findById(featureId).get();
                    return new ResponseEntity(feature, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Device #" + featureId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/features")
	public ResponseEntity createFeatures(@Valid @RequestBody Features feature) {
                try{
                    Features d = featuresRepository.save(feature);
                    return new ResponseEntity(d, HttpStatus.CREATED);
                }
                catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Device", HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/features/{id}")
	public ResponseEntity updateFeatures(
			@PathVariable(value = "id") Long deviceId,
			@Valid @RequestBody Features featureDetails){
                if(featuresRepository.existsById(deviceId)){
                    Features feature = featuresRepository.findById(deviceId).get();
                    feature.setAlimentacion(featureDetails.getAlimentacion());
                    feature.setFirmware(featureDetails.getFirmware());
                    feature.setMemoria(featureDetails.isMemoria());
                    feature.setModos_transmision(featureDetails.getModos_transmision());
                    feature.setModelo(featureDetails.getModelo());
                    feature.setNivel_voltaje(feature.getNivel_voltaje());
                    feature.setTracking(featureDetails.isTracking());
                    feature.setVersion(featureDetails.getVersion());
                    feature.setTipo_sensores(featureDetails.getTipo_sensores());
                    //feature.setDevice(featureDetails.getDevice());
                    final Features updatedFeature = featuresRepository.save(feature);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Device #" + deviceId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/features/{id}")
	public ResponseEntity deleteFeatures(
			@PathVariable(value = "id") Long featureId){
                if(featuresRepository.existsById(featureId)){
                    Features feature = featuresRepository.findById(featureId).get();
                    
                    featuresRepository.delete(feature);
                    return new ResponseEntity(HttpStatus.OK); 
                }
		else{
                    return new ResponseEntity<String>("Feature #" + featureId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
