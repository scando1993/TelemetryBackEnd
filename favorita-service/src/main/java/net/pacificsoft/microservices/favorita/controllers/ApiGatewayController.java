
package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.springframework.http.HttpStatus;
public class ApiGatewayController {
    
    
        @Autowired
	private RawSensorDataRepository rawDataRepository;
        
	@Autowired
	private DeviceRepository deviceRepository;
        
        
        @PostMapping("/rawData")
	public ResponseEntity createCiudad(@Valid @RequestBody RawSensorData rawData) {
            try{
                RawSensorData rawSave = rawDataRepository.save(rawData);
                return new ResponseEntity(HttpStatus.OK);
                
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Data", HttpStatus.NOT_FOUND);
                
            }
	}
}
