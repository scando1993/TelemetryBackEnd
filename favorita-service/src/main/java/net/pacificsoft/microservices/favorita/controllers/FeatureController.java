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
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.FeaturesRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class FeatureController {
	
	@Autowired
	private FeaturesRepository featuresRepository;
        
        @Autowired
	private TrackingRepository trackingRepository;
	
	@GetMapping("/features")
	public ResponseEntity getAllDevices() {
		try{
                    return new ResponseEntity(featuresRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	/*@GetMapping("/device/{id}")
	public ResponseEntity getDeviceById(
			@PathVariable(value = "id") Long deviceId){
		if(deviceRepository.existsById(deviceId)){
                    Device device = deviceRepository.findById(deviceId).get();
                    return new ResponseEntity(device, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Device #" + deviceId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/device")
	public ResponseEntity createDevice(@Valid @RequestBody Device device) {
                try{
                    Device d = deviceRepository.save(device);
                    return new ResponseEntity(d, HttpStatus.CREATED);
                }
                catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Device", HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/device/{id}")
	public ResponseEntity updateDevice(
			@PathVariable(value = "id") Long deviceId,
			@Valid @RequestBody Device deviceDetails){
                if(deviceRepository.existsById(deviceId)){
                    Device device = deviceRepository.findById(deviceId).get();
                    device.setFamily(deviceDetails.getFamily());
                    device.setName(deviceDetails.getName());
                    final Device updatedDevice = deviceRepository.save(device);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Device #" + deviceId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/device/{id}")
	public ResponseEntity deleteDevice(
			@PathVariable(value = "id") Long deviceId){
                if(deviceRepository.existsById(deviceId)){
                    Device device = deviceRepository.findById(deviceId).get();
                    for(Tracking t:device.getTrackings()){
                        t.setDevice(null);
                        trackingRepository.save(t);
                    }
                    deviceRepository.delete(device);
                    return new ResponseEntity(HttpStatus.OK); 
                }
		else{
                    return new ResponseEntity<String>("Device #" + deviceId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}*/
}
