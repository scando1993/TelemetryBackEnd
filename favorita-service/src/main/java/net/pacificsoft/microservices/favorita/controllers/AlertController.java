package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Alert;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.AlertRepository;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
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
public class AlertController {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AlertRepository alertaRepository;

    @GetMapping("/alert")
    public ResponseEntity getAllAlerta() {
        try{
            List<Map<String, Object>> result = new ArrayList();
            List<Alert> alerts = alertaRepository.findAll();
            JSONObject json;
            for(Alert a: alerts){
                json = new JSONObject();
                json.put("id", a.getId());
                json.put("type_alert", a.getType_alert());
                json.put("message", a.getMessage());
                json.put("rutaID", a.getRuta().getId());
                json.put("deviceID", a.getDevice().getId());
                result.add(json.toMap());
            }
            return new ResponseEntity(rutaRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/alert/{id}")
    public ResponseEntity getAlertaById(
            @PathVariable(value = "id") Long alertaId){
        if(rutaRepository.exists(alertaId)){
            Alert a = alertaRepository.findOne(alertaId);
            JSONObject json = new JSONObject();
            json.put("id", a.getId());
            json.put("type_alert", a.getType_alert());
            json.put("message", a.getMessage());
            json.put("rutaID", a.getRuta().getId());
            json.put("deviceID", a.getDevice().getId());
            return new ResponseEntity(json.toMap(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Alert #" + alertaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/alert/{deviceid}/{rutaid}")
    public ResponseEntity createAlerta(@PathVariable(value = "deviceid") Long deviceid,
                                       @PathVariable(value = "rutaid") Long rutaid,
                                       @Valid @RequestBody Alert alert) {
        try{
            if(rutaid!=0){
                if(deviceRepository.exists(deviceid) &&
                        rutaRepository.exists(rutaid)){
                    Ruta ruta = rutaRepository.findOne(rutaid);
                    ruta.getAlerts().add(alert);
                    Device device = deviceRepository.findOne(deviceid);
                    device.getAlerts().add(alert);
                    alert.setRuta(ruta);
                    alert.setDevice(device);
                    Alert a = alertaRepository.save(alert);
                    rutaRepository.save(ruta);
                    deviceRepository.save(device);
                    return new ResponseEntity(a, HttpStatus.CREATED);
                }
            }
            else{
                if(deviceRepository.exists(deviceid)){
                    Device device = deviceRepository.findOne(deviceid);
                    device.getAlerts().add(alert);
                    alert.setDevice(device);
                    Alert a = alertaRepository.save(alert);
                    deviceRepository.save(device);
                    return new ResponseEntity(a, HttpStatus.CREATED);
                }
            }
        }catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Alert",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("It's not possible create new Alert",
                HttpStatus.NOT_FOUND);
    }

    @PutMapping("/alert/{id}")
    public ResponseEntity updateAlerta(
            @PathVariable(value = "id") Long alertaId,
            @Valid @RequestBody Alert alertDetails){
        if(alertaRepository.exists(alertaId)){
            Alert alert = alertaRepository.findOne(alertaId);
            alert.setMessage(alertDetails.getMessage());
            alert.setType_alert(alertDetails.getType_alert());
            final Alert updatedAlert = alertaRepository.save(alert);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Alert #" + alertaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/alerts/{id}")
    public ResponseEntity deleteAlerta(
            @PathVariable(value = "id") Long alertaId){
        if(alertaRepository.exists(alertaId)){
            Alert alert = alertaRepository.findOne(alertaId);
            alert.getRuta().setAlerts(null);
            alert.getDevice().setAlerts(null);
            rutaRepository.save(alert.getRuta());
            deviceRepository.save(alert.getDevice());
            alertaRepository.delete(alert);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Alert #" + alertaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

}
