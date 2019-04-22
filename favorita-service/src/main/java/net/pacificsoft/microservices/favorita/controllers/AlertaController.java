package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.application.Ruta;
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
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class AlertaController {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AlertaRepository alertaRepository;

    @GetMapping("/alerta")
    public ResponseEntity getAllAlerta() {
        try{
            return new ResponseEntity(alertaRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/alerta/{id}")
    public ResponseEntity getAlertaById(
            @PathVariable(value = "id") Long alertaId){
        if(alertaRepository.existsById(alertaId)){
            Alerta a = alertaRepository.findById(alertaId).get();
            return new ResponseEntity(a, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Alerta #" + alertaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/alerta/{deviceid}/{rutaid}")
    public ResponseEntity createAlerta(@PathVariable(value = "deviceid") Long deviceid,
                                       @PathVariable(value = "rutaid") Long rutaid,
                                       @Valid @RequestBody Alerta alerta) {
        try{
            if(rutaid!=0){
                if(deviceRepository.existsById(deviceid) &&
                        rutaRepository.existsById(rutaid)){
                    Ruta ruta = rutaRepository.findById(rutaid).get();
                    ruta.getAlertas().add(alerta);
                    Device device = deviceRepository.findById(deviceid).get();
                    device.getAlertas().add(alerta);
                    alerta.setRuta(ruta);
                    alerta.setDevice(device);
                    Alerta a = alertaRepository.save(alerta);
                    rutaRepository.save(ruta);
                    deviceRepository.save(device);
                    return new ResponseEntity(a, HttpStatus.CREATED);
                }
            }
            else{
                if(deviceRepository.existsById(deviceid)){
                    Device device = deviceRepository.findById(deviceid).get();
                    device.getAlertas().add(alerta);
                    alerta.setDevice(device);
                    Alerta a = alertaRepository.save(alerta);
                    deviceRepository.save(device);
                    return new ResponseEntity(a, HttpStatus.CREATED);
                }
            }
        }catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Alerta",
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("It's not possible create new Alerta",
                HttpStatus.NOT_FOUND);
    }

    @PutMapping("/alerta/{id}")
    public ResponseEntity updateAlerta(
            @PathVariable(value = "id") Long alertaId,
            @Valid @RequestBody Alerta alertaDetails){
        if(alertaRepository.existsById(alertaId)){
            Alerta alerta = alertaRepository.findById(alertaId).get();
            alerta.setMensaje(alertaDetails.getMensaje());
            alerta.setTypeAlert(alertaDetails.getTypeAlert());
            final Alerta updatedAlerta = alertaRepository.save(alerta);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Alerta #" + alertaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/alerta/{id}")
    public ResponseEntity deleteAlerta(
            @PathVariable(value = "id") Long alertaId){
        if(alertaRepository.existsById(alertaId)){
            Alerta alerta = alertaRepository.findById(alertaId).get();
            alerta.getRuta().setAlertas(null);
            alerta.getDevice().setAlertas(null);
            rutaRepository.save(alerta.getRuta());
            deviceRepository.save(alerta.getDevice());
            alertaRepository.delete(alerta);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Alerta #" + alertaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

}
