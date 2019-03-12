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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Status;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.StatusRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class StatusController {
	
	@Autowired
	private StatusRepository statusRepository;
        
        @Autowired
	private DeviceRepository deviceRepository;
	@GetMapping("/status")
	public ResponseEntity getAllStatus() {
		try{
                    List<Map<String, Object>> result = new ArrayList();
                    List<Status> status = statusRepository.findAll();
                    JSONObject json;
                    for(Status s : status){
                        json = new JSONObject();
                        json.put("id", s.getId());
                        json.put("batery", s.getBatery());
                        json.put("last_transmision", s.getLast_transmision());
                        json.put("signal_level", s.getSignal_level());
                        json.put("last_update", s.getLast_update());
                        json.put("device_name", s.getDevice().getName());
                        json.put("device_family", s.getDevice().getName());
                        result.add(json.toMap());
                    }
                    return new ResponseEntity(result, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/status/{id}")
	public ResponseEntity getStatusById(
			@PathVariable(value = "id") Long statusId){
		if(statusRepository.existsById(statusId)){
                    Status s = statusRepository.findById(statusId).get();
                    JSONObject json = new JSONObject();
                    json.put("id", s.getId());
                    json.put("batery", s.getBatery());
                    json.put("last_transmision", s.getLast_transmision());
                    json.put("signal_level", s.getSignal_level());
                    json.put("last_update", s.getLast_update());
                    json.put("device_name", s.getDevice().getName());
                    json.put("device_family", s.getDevice().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Status #" + statusId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/status/{deviceid}")
	public ResponseEntity createStatus(@PathVariable(value = "deviceid") Long deviceid,
                                  @Valid @RequestBody Status status) {
            try{
                if(deviceRepository.existsById(deviceid)){
                    Device device = deviceRepository.findById(deviceid).get();
                    device.setStatus(status);
                    status.setDevice(device);
                    Status s = statusRepository.save(status);
                    deviceRepository.save(device);
                    JSONObject json = new JSONObject();
                    json.put("id", s.getId());
                    json.put("batery", s.getBatery());
                    json.put("last_transmision", s.getLast_transmision());
                    json.put("signal_level", s.getSignal_level());
                    json.put("last_update", s.getLast_update());
                    json.put("device_name", s.getDevice().getName());
                    json.put("device_family", s.getDevice().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
                }
		else{
                    return new ResponseEntity<String>("Device #" + deviceid + 
                            " does not exist, it isn't possible create new Status", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible create new Status", HttpStatus.NOT_FOUND);
            }
	}

	@PutMapping("/status/{id}")
	public ResponseEntity updateStatus(
			@PathVariable(value = "id") Long statusid,
			@Valid @RequestBody Status statusDetails){
            try{
                if(statusRepository.existsById(statusid)){
                    Status status = statusRepository.findById(statusid).get();
                    status.setBatery(statusDetails.getBatery());
                    status.setLast_transmision(statusDetails.getLast_transmision());
                    status.setSignal_level(statusDetails.getSignal_level());
                    status.setLast_update(statusDetails.getLast_update());
                    final Status updatedStatus = statusRepository.save(status);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Status #" + statusid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Status #" + statusid, HttpStatus.NOT_FOUND);
            }
	}

	@DeleteMapping("/status/{id}")
	public ResponseEntity deleteStatus(
			@PathVariable(value = "id") Long statusId){
                if(statusRepository.existsById(statusId)){
                    Status status = statusRepository.findById(statusId).get();
                    status.getDevice().setStatus(null);
                    deviceRepository.save(status.getDevice());
                    statusRepository.delete(status);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Status #" + statusId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
