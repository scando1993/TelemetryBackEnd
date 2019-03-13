package net.pacificsoft.microservices.favorita.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.models.application.Bodega;
import net.pacificsoft.microservices.favorita.repository.application.BodegaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.application.Ciudad;
import net.pacificsoft.microservices.favorita.models.LocationPriority;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.application.CiudadRepository;
import net.pacificsoft.microservices.favorita.repository.LocationPriorityRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class LocationPriorityController {
    
	@Autowired
	private LocationPriorityRepository locationPriorityRepository;
        
        @Autowired
	private TrackingRepository trackingRepository;
	
	@GetMapping("/locationPriority")
	public ResponseEntity getAllBodegas() {
                try{
                    
                    return new ResponseEntity(locationPriorityRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                }  
	}

	@GetMapping("/locationPriority/{id}")
	public ResponseEntity getBodegaById(
			@PathVariable(value = "id") Long lPriorityiD){
                if(locationPriorityRepository.existsById(lPriorityiD)){
                    LocationPriority lp = locationPriorityRepository.findById(lPriorityiD).get();
                    return new ResponseEntity(lp, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("LocationPriority #" + lPriorityiD + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/locationPriority/{trackingid}")
	public ResponseEntity createBodega(@PathVariable(value = "trackingid") Long trackingId,
                                 @Valid @RequestBody LocationPriority locationPriority) {
            try{
                if(trackingRepository.existsById(trackingId)){
                    Tracking tracking = trackingRepository.findById(trackingId).get();
                    tracking.getLocationPrioritys().add(locationPriority);
                    locationPriority.setTracking(tracking);
                    LocationPriority lP = locationPriorityRepository.save(locationPriority);
                    trackingRepository.save(tracking);
                    return new ResponseEntity(lP, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Tracking #" + trackingId + 
                            " does not exist, it's not possible create new LocationPriorirty", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new LocationPriority", HttpStatus.NOT_FOUND);
                
            }
	}

        @PutMapping("/locationPriority/{id}")
	public ResponseEntity updateBodega(
			@PathVariable(value = "id") Long lpId,
			@Valid @RequestBody LocationPriority lpDetails){
            try{
                if(locationPriorityRepository.existsById(lpId)) {
                    LocationPriority locationP = locationPriorityRepository.findById(lpId).get();
                    locationP.setName(lpDetails.getName());
                    locationP.setPriority(lpDetails.getPriority());
                    final LocationPriority lpUpdate = locationPriorityRepository.save(locationP); 
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("LocationPriority #" + lpId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update "
                        + "LocationPriority #" + lpId, HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/locationPriority/{id}")
	public ResponseEntity deleteBodega(
                                @PathVariable(value = "id") Long lpId){
                if(locationPriorityRepository.existsById(lpId)){
                    LocationPriority lp = locationPriorityRepository.findById(lpId).get();
                    lp.getTracking().getLocationPrioritys().remove(lp);
                    trackingRepository.save(lp.getTracking());
                    locationPriorityRepository.delete(lp);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("LocationPriority #" + lpId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
