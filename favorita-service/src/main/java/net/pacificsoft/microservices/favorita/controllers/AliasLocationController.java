package net.pacificsoft.microservices.favorita.controllers;

import com.companyname.springbootcrudrest.model.AliasLocation;
import com.companyname.springbootcrudrest.model.Location;
import com.companyname.springbootcrudrest.repository.AliasLocationRepository;
import com.companyname.springbootcrudrest.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class AliasLocationController {
	
	@Autowired
	private AliasLocationRepository aliasLocationRepository;
        
        @Autowired
	private LocationRepository locationRepository;
	
	@GetMapping("/aliasLocation")
	public ResponseEntity getAllAliasLocation() {
		try{
                    return new ResponseEntity(aliasLocationRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/aliasLocation/{id}")
	public ResponseEntity getAliasLocationById(
			@PathVariable(value = "id") Long alLocationId){
		if(aliasLocationRepository.existsById(alLocationId)){
                    AliasLocation alLocation = aliasLocationRepository.findById(alLocationId).get();
                    return new ResponseEntity(alLocation, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("AliasLocation #" + alLocationId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/aliasLocation/{locationid}")
	public ResponseEntity createAliasLocation(@PathVariable(value = "locationid") Long locationid,
                               @Valid @RequestBody AliasLocation alLocation) {
                if(locationRepository.existsById(locationid)){
                    Location l = locationRepository.findById(locationid).get();
                    l.setAliasLocation(alLocation);
                    alLocation.setLocation(l);
                    AliasLocation al = aliasLocationRepository.save(alLocation);
                    locationRepository.save(l);
                    return new ResponseEntity(al, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Location #" + locationid  +
                            " does not exist, isn't possible create new AliasLocation", HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/aliasLocation/{id}")
	public ResponseEntity updateAliasLocation(
			@PathVariable(value = "id") Long alLocationId,
			@Valid @RequestBody AliasLocation alLocationDetails){
                if(aliasLocationRepository.existsById(alLocationId)){
                    AliasLocation alLocation = aliasLocationRepository.findById(alLocationId).get();
                    alLocation.setName(alLocationDetails.getName());
                    final AliasLocation updatedAlLoc = aliasLocationRepository.save(alLocation);
                    return new ResponseEntity(updatedAlLoc, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("AliasLocation #" + alLocationId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/aliasLocation/{id}")
	public ResponseEntity deleteAliasLocation(
			@PathVariable(value = "id") Long alLocationId){
                if(aliasLocationRepository.existsById(alLocationId)){
                    AliasLocation alLocation = aliasLocationRepository.findById(alLocationId).get();
                    alLocation.getLocation().setAliasLocation(null);
                    locationRepository.save(alLocation.getLocation());
                    aliasLocationRepository.delete(alLocation);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("AliasLocation #" + alLocationId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
