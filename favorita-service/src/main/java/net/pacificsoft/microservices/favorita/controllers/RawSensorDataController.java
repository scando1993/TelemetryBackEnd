package net.pacificsoft.springbootcrudrest.controller;

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
import net.pacificsoft.springbootcrudrest.model.Ciudad;
import net.pacificsoft.springbootcrudrest.model.Device;
import net.pacificsoft.springbootcrudrest.model.Provincia;
import net.pacificsoft.springbootcrudrest.model.RawSensorData;
import net.pacificsoft.springbootcrudrest.repository.CiudadRepository;
import net.pacificsoft.springbootcrudrest.repository.DeviceRepository;
import net.pacificsoft.springbootcrudrest.repository.ProvinciaRepository;
import net.pacificsoft.springbootcrudrest.repository.RawSensorDataRepository;
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
                if(rawSensorDataRepository.exists(rawId)){
                    RawSensorData rawD = rawSensorDataRepository.findOne(rawId);
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
                if(deviceRepository.exists(deviceId)){
                    Device device = deviceRepository.findOne(deviceId);
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
                if(rawSensorDataRepository.exists(rawId)) {
                    RawSensorData rawD = rawSensorDataRepository.findOne(rawId);
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
                if(rawSensorDataRepository.exists(rawId)){
                    RawSensorData rawD = rawSensorDataRepository.findOne(rawId);
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
