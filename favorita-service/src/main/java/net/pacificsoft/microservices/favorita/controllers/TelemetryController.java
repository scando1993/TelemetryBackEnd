package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Telemetry;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.TelemetryRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class TelemetryController {

    @Autowired
    private TelemetryRepository telemetryRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/telemetria")
    public ResponseEntity getAllTelemetrias() {
        try{
            List<Map<String, Object>> result = new ArrayList();
            List<Telemetry> telemetries = telemetryRepository.findAll();
            JSONObject json;
            for(Telemetry t: telemetries){
                json = new JSONObject();
                json.put("id", t.getId());
                json.put("Dtm", t.getDtm());
                json.put("name", t.getName());
                json.put("value", t.getValue());
                json.put("device_name", t.getDevice().getName());
                json.put("device_family", t.getDevice().getName());
                result.add(json.toMap());
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/telemetria/{id}")
    public ResponseEntity getTelemetriaById(
            @PathVariable(value = "id") Long telemetriaId){
        if(telemetryRepository.exists(telemetriaId)){
            Telemetry t = telemetryRepository.findOne(telemetriaId);
            JSONObject json = new JSONObject();
            json.put("id", t.getId());
            json.put("Dtm", t.getDtm());
            json.put("name", t.getName());
            json.put("value", t.getValue());
            json.put("device_name", t.getDevice().getName());
            json.put("device_family", t.getDevice().getName());
            return new ResponseEntity(json.toMap(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Telemetry #" + telemetriaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/telemetria/{deviceid}")
    public ResponseEntity createTelemetria(@PathVariable(value = "deviceid") Long deviceid,
                                           @Valid @RequestBody Telemetry telemetry) {
        if(deviceRepository.exists(deviceid)){
            Device device = deviceRepository.findOne(deviceid);
            device.getTelemetries().add(telemetry);
            telemetry.setDevice(device);
            Telemetry t = telemetryRepository.save(telemetry);
            deviceRepository.save(device);
            JSONObject json = new JSONObject();
            json.put("id", t.getId());
            json.put("Dtm", t.getDtm());
            json.put("name", t.getName());
            json.put("value", t.getValue());
            json.put("device_name", t.getDevice().getName());
            json.put("device_family", t.getDevice().getName());
            return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<String>("It's not possible create new Telemetry",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/telemetria/{id}")
    public ResponseEntity updateTelemetria(
            @PathVariable(value = "id") Long telemetriaId,
            @Valid @RequestBody Telemetry telemetryDetails){
        if(telemetryRepository.exists(telemetriaId)){
            Telemetry telemetry = telemetryRepository.findOne(telemetriaId);
            telemetry.setName(telemetryDetails.getName());
            telemetry.setDtm(telemetryDetails.getDtm());
            telemetry.setValue(telemetryDetails.getValue());
            final Telemetry updatedTelemetry = telemetryRepository.save(telemetry);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Telemetry #" + telemetriaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/telemetria/{id}")
    public ResponseEntity deleteTelemetria(
            @PathVariable(value = "id") Long telemetriaId){
        if(telemetryRepository.exists(telemetriaId)){
            Telemetry telemetry = telemetryRepository.findOne(telemetriaId);
            telemetry.getDevice().getTelemetries().remove(telemetry);
            deviceRepository.save(telemetry.getDevice());
            telemetryRepository.delete(telemetry);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Telemetry #" + telemetriaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
