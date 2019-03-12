package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
