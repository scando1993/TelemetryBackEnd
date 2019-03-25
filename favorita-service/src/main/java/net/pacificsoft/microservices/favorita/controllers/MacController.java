package net.pacificsoft.microservices.favorita.controllers;

import javax.validation.Valid;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.GoApiResponse;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.models.Mac;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.GoApiResponseRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;
import net.pacificsoft.microservices.favorita.repository.MacRepository;
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
public class MacController {
	
	@Autowired
	private MacRepository macRepository;
        
        
	@GetMapping("/mac")
	public ResponseEntity getAllDevices() {
		try{
                    return new ResponseEntity(macRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/mac/{id}")
	public ResponseEntity getDeviceById(
			@PathVariable(value = "id") Long macId){
		if(macRepository.existsById(macId)){
                    Mac mac = macRepository.findById(macId).get();
                    return new ResponseEntity(mac, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Mac #" + macId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/mac")
	public ResponseEntity createDevice(@Valid @RequestBody Mac mac) {
                try{
                    
                        Mac m  = macRepository.save(mac);
                        return new ResponseEntity(m, HttpStatus.CREATED);
                }
                catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Device", HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/mac/{id}")
	public ResponseEntity updateDevice(
			@PathVariable(value = "id") Long macId,
			@Valid @RequestBody Mac macDetails){
                if(macRepository.existsById(macId)){
                    Mac mac = macRepository.findById(macId).get();
                    mac.setFamily(macDetails.getFamily());
                    mac.setMac(macDetails.getMac());
                    //device.setUuid(deviceDetails.getUuid());
                    final Mac updatedMac = macRepository.save(mac);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Mac #" + macId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/mac/{id}")
	public ResponseEntity deleteDevice(
			@PathVariable(value = "id") Long macId){
                if(macRepository.existsById(macId)){
                    macRepository.delete(macRepository.findById(macId).get());
                    return new ResponseEntity(HttpStatus.OK); 
                }
		else{
                    return new ResponseEntity<String>("Mac #" + macId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
