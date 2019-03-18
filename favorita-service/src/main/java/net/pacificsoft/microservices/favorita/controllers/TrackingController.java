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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.exception.ResourceNotFoundException;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import java.util.Set;
import net.pacificsoft.microservices.favorita.models.LocationGroup;
import net.pacificsoft.microservices.favorita.repository.LocationGroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class TrackingController {
	
	@Autowired
	private TrackingRepository trackingRepository;
        
        @Autowired
	private DeviceRepository deviceRepository;
        
        @Autowired
	private LocationGroupRepository locationGroupRepository;

	@Autowired
	private RutaRepository rutaRepository;
        
	@GetMapping("/tracking")
	public ResponseEntity getAllTrackings() {
		try{
                    return new ResponseEntity(trackingRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/tracking/{id}")
	public ResponseEntity getTrackingById(
			@PathVariable(value = "id") Long trackingId){
		if(trackingRepository.existsById(trackingId)){
                    Tracking tracking = trackingRepository.findById(trackingId).get();
                    return new ResponseEntity(tracking, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Tracking #" + trackingId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/tracking/{deviceid}/{locationid}")
	public ResponseEntity createTracking(@Valid @RequestBody Tracking tracking,
                                @PathVariable(value = "deviceid") Long deviceId,
                                @PathVariable(value = "locationid") Long locationId) {
		try{
                    if(deviceRepository.existsById(deviceId) &&
                       locationGroupRepository.existsById(locationId)){
                        Device device = deviceRepository.findById(deviceId).get();
                        LocationGroup location = locationGroupRepository.findById(locationId).get();
                        device.getTrackings().add(tracking);
                        location.getTrackings().add(tracking);
                        tracking.setDevice(device);
                        tracking.setLocationGroup(location);
                        Tracking t = trackingRepository.save(tracking);
                        deviceRepository.save(device);
                        locationGroupRepository.save(location);
                        return new ResponseEntity(t, HttpStatus.CREATED);
                    }
                    else{
                        return new ResponseEntity<String>("New Tracking not created.",
                            HttpStatus.CONFLICT);
                    }
                }
		catch(Exception e){
                    return new ResponseEntity<String>("New Tracking not created.",
                            HttpStatus.BAD_REQUEST);
                }
	}

	@PutMapping("/tracking/{id}/{deviceid}/{locationid}")
	public ResponseEntity updateTracking(
			@PathVariable(value = "id") Long trackingId,
                        @PathVariable(value = "deviceid") Long deviceId,
                        @PathVariable(value = "locationid") Long locationId,
			@Valid @RequestBody Tracking trackingDetails){
                if(trackingRepository.existsById(trackingId) &&
                   deviceRepository.existsById(deviceId) &&
                   locationGroupRepository.existsById(locationId)){
                    Tracking tracking = trackingRepository.findById(trackingId).get();
                    Device device = deviceRepository.findById(deviceId).get();
                    LocationGroup location = locationGroupRepository.findById(locationId).get();
                    device.getTrackings().add(tracking);
                    location.getTrackings().add(tracking);
                    tracking.setLocation(trackingDetails.getLocation());
                    tracking.setDtm(trackingDetails.getDtm());
                    tracking.setDevice(device);
                    tracking.setLocationGroup(location);
                    final Tracking updatedTracking = trackingRepository.save(tracking);
                    deviceRepository.save(device);
                    locationGroupRepository.save(location);
                    return new ResponseEntity(HttpStatus.OK);
                }
		
		else{
                    return new ResponseEntity<String>("Tracking #" + trackingId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/tracking/{id}")
	public ResponseEntity deleteTracking(
			@PathVariable(value = "id") Long trackingId){
                if(trackingRepository.existsById(trackingId)){
                    Tracking tracking = trackingRepository.findById(trackingId).get();
                    tracking.getDevice().getTrackings().remove(tracking);
                    tracking.getLocationGroup().getTrackings().remove(tracking);
                    deviceRepository.save(tracking.getDevice());
                    locationGroupRepository.save(tracking.getLocationGroup());
                    trackingRepository.delete(tracking);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Tracking #" + trackingId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}  
}
