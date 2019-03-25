package net.pacificsoft.microservices.favorita.controllers;

import javax.validation.Valid;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.GoApiResponse;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.GoApiResponseRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class DeviceController {
	
	@Autowired
	private DeviceRepository deviceRepository;
        
        @Autowired
	private TrackingRepository trackingRepository;
	
        @Autowired
	private GroupRepository groupRepository;
        
        @Autowired
	private GoApiResponseRepository goRepository;
        
	@GetMapping("/device")
	public ResponseEntity getAllDevices() {
		try{
                    return new ResponseEntity(deviceRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/device/{id}")
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

	@PostMapping("/device/{groupid}")
	public ResponseEntity createDevice(@Valid @RequestBody Device device,
                                        @PathVariable(value = "groupid") Long groupId) {
                try{
                    if(groupRepository.existsById(groupId)){
                        Group g = groupRepository.findById(groupId).get();
                        g.getDevices().add(device);
                        device.setGroup(g);
                        Device d = deviceRepository.save(device);
                        groupRepository.save(g);
                        return new ResponseEntity(d, HttpStatus.CREATED);
                    }
                    else{
                        return new ResponseEntity<String>("It's not possible create new Device", HttpStatus.NOT_FOUND);

                    }
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
                    //device.setUuid(deviceDetails.getUuid());
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
                    
                    for(GoApiResponse g:device.getGoApiResponses()){
                        g.setDevice(null);
                        goRepository.save(g);
                    }
                    device.getGroup().getDevices().remove(device);
                    groupRepository.save(device.getGroup());
                    deviceRepository.delete(device);
                    return new ResponseEntity(HttpStatus.OK); 
                }
		else{
                    return new ResponseEntity<String>("Device #" + deviceId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
