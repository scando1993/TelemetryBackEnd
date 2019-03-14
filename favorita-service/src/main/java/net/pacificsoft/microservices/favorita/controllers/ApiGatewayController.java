
package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.Valid;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiGatewayController {
    
    
    @Autowired
	private RawSensorDataRepository rawDataRepository;
        
	@Autowired
	private DeviceRepository deviceRepository;


    @PostMapping("/rawData/{deviceid}")
    public ResponseEntity createCiudad(
            @PathVariable(value = "deviceid") Long deviceId,
            @Valid @RequestBody RawSensorData rawData) {
        try{
                Device device = deviceRepository.findById(deviceId).get();
                device.getRawSensorDatas().add(rawData);
                rawData.setDevice(device);


            RawSensorData rawSave = rawDataRepository.save(rawData);
            return new ResponseEntity(rawData,HttpStatus.CREATED);

        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Data", HttpStatus.NOT_FOUND);

        }
    }
}
