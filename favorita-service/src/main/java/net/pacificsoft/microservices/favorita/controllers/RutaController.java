package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Furgon;
import net.pacificsoft.microservices.favorita.models.Ruta;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.FurgonRepository;
import net.pacificsoft.microservices.favorita.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class RutaController {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private FurgonRepository furgonRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/ruta")
    public ResponseEntity getAllRutas() {
        try {
            return new ResponseEntity(rutaRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ruta/{id}")
    public ResponseEntity getRutaById(
            @PathVariable(value = "id") Long rutaId) {
        if (rutaRepository.existsById(rutaId)) {
            Ruta ruta = rutaRepository.findById(rutaId).get();
            return new ResponseEntity(ruta, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Ruta #" + rutaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ruta/{furgonid}/{deviceid}")
    public ResponseEntity createRuta(@PathVariable(value = "furgonid") Long furgonid,
                                     @PathVariable(value = "deviceid") Long deviceid,
                                     @Valid @RequestBody Ruta ruta) {
        System.out.println(ruta);
        if (deviceRepository.existsById(deviceid) &&
                furgonRepository.existsById(furgonid)) {
            Furgon furgon = furgonRepository.findById(furgonid).get();
            Device device = deviceRepository.findById(deviceid).get();
            furgon.setRuta(ruta);
            ruta.setDevice(device);
            device.setRuta(ruta);
            ruta.setFurgon(furgon);
            Ruta r = rutaRepository.save(ruta);
            furgonRepository.save(furgon);
            deviceRepository.save(device);
            return new ResponseEntity(r, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Device #" + deviceid + " or furgon #" + furgonid +
                    " does not exist, isn't possible create new Ruta", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/ruta/{id}")
    public ResponseEntity updateRuta(
            @PathVariable(value = "id") Long rutaId,
            @Valid @RequestBody Ruta rutaDetails) {
        if (rutaRepository.existsById(rutaId)) {
            Ruta ruta = rutaRepository.findById(rutaId).get();
            ruta.setStart_date(rutaDetails.getStart_date());
            ruta.setEnd_date(rutaDetails.getEnd_date());
            ruta.setStart_hour(rutaDetails.getStart_hour());
            ruta.setEnd_hour(rutaDetails.getEnd_hour());
            ruta.setTemp_max_ap(rutaDetails.getTemp_max_ap());
            ruta.setTemp_min_ap(rutaDetails.getTemp_min_ap());
            ruta.setTemp_max_ideal(rutaDetails.getTemp_max_ideal());
            ruta.setTemp_min_ideal(rutaDetails.getTemp_min_ideal());
            final Ruta updatedRuta = rutaRepository.save(ruta);
            return new ResponseEntity(updatedRuta, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Ruta #" + rutaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ruta/{id}")
    public ResponseEntity deleteRuta(
            @PathVariable(value = "id") Long rutaId) {
        if (rutaRepository.existsById(rutaId)) {
            Ruta ruta = rutaRepository.findById(rutaId).get();
            ruta.getDevice().setRuta(null);
            ruta.getFurgon().setRuta(null);
            furgonRepository.save(ruta.getFurgon());
            deviceRepository.save(ruta.getDevice());
            rutaRepository.delete(ruta);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Ruta #" + rutaId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
