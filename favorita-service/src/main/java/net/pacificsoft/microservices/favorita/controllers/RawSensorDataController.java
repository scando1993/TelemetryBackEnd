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
import net.pacificsoft.microservices.favorita.models.Ciudad;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Provincia;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.repository.CiudadRepository;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.ProvinciaRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class RawSensorDataController {
    
	@Autowired
	private RawSensorDataRepository rawSensorDataRepository;
        
	@Autowired
	private DeviceRepository deviceRepository;
        
	@GetMapping("/rawSensorData")
	public ResponseEntity getAllCiudad() {
                try{
                    return new ResponseEntity(rawSensorDataRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                }  
	}

	@GetMapping("/rawSensorData/{id}")
	public ResponseEntity getCiudadById(
			@PathVariable(value = "id") Long rawId){
                if(rawSensorDataRepository.existsById(rawId)){
                    RawSensorData rawD = rawSensorDataRepository.findById(rawId).get();
                    return new ResponseEntity(rawD, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("RawSensorData #" + rawId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/rawSensorData/{deviceid}")
	public ResponseEntity createCiudad(@PathVariable(value = "deviceid") Long deviceId,
                                 @Valid @RequestBody RawSensorData rawData) {
            try{
                if(deviceRepository.existsById(deviceId)){
                    Device device = deviceRepository.findById(deviceId).get();
                    rawData.setDevice(device);
                    device.getRawSensorDatas().add(rawData);
                    RawSensorData r = rawSensorDataRepository.save(rawData);
                    deviceRepository.save(device);
                    return new ResponseEntity(r, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Device #" + deviceId + 
                            " does not exist, it's not possible create new RawSensorData", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new RawSensorData", HttpStatus.NOT_FOUND);
                
            }
	}

        @PutMapping("/rawSensorData/{id}")
	public ResponseEntity updateCiudad(
			@PathVariable(value = "id") Long rawId,
			@Valid @RequestBody RawSensorData rawDetails){
            try{
                if(rawSensorDataRepository.existsById(rawId)) {
                    RawSensorData rawD = rawSensorDataRepository.findById(rawId).get();
                    rawD.setEpoch(rawDetails.getEpoch());
                    rawD.setEpochDateTime(rawDetails.getEpochDateTime());
                    rawD.setRawData(rawDetails.getRawData());
                    rawD.setTemperature(rawDetails.getTemperature());
                    final RawSensorData updatedRaw = rawSensorDataRepository.save(rawD);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("RawSensorData #" + rawId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update "
                        + "RawId #" + rawId, HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/rawSensorData/{id}")
	public ResponseEntity deleteCiudad(
                                @PathVariable(value = "id") Long rawId){
                if(rawSensorDataRepository.existsById(rawId)){
                    RawSensorData rawD = rawSensorDataRepository.findById(rawId).get();
                    rawD.getDevice().getRawSensorDatas().remove(rawD);
                    deviceRepository.save(rawD.getDevice());
                    rawSensorDataRepository.delete(rawD);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("RawSensorData #" + rawId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
