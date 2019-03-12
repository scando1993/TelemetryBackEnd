package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Ubicacion;
import net.pacificsoft.microservices.favorita.models.UbicacionFurgon;
import net.pacificsoft.microservices.favorita.repository.FurgonRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionFurgonRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class UbicacionController {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private UbicacionFurgonRepository ubicacionFurgonRepository;

    @Autowired
    private FurgonRepository furgonRepository;

    @GetMapping("/ubicacion")
    public ResponseEntity getAllUbicacion() {
        try {
            return new ResponseEntity(ubicacionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ubicacion/{id}")
    public ResponseEntity getUbicacionById(
            @PathVariable(value = "id") Long ubicacionId) {
        if (ubicacionRepository.existsById(ubicacionId)) {
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionId).get();
            return new ResponseEntity(ubicacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Ubicacion #" + ubicacionId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ubicacion")
    public ResponseEntity createUbicacion(@Valid @RequestBody Ubicacion ubicacion) {
        try {
            Ubicacion u = ubicacionRepository.save(ubicacion);
            return new ResponseEntity(u, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("New Ubicacion not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/ubicacion/{id}")
    public ResponseEntity updateUbicacion(
            @PathVariable(value = "id") Long ubicacionId,
            @Valid @RequestBody Ubicacion ubicacionDetails) {
        if (ubicacionRepository.existsById(ubicacionId)) {
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionId).get();
            ubicacion.setCity(ubicacionDetails.getCity());
            ubicacion.setProvince(ubicacionDetails.getProvince());
            ubicacion.setRegional(ubicacionDetails.getRegional());
            ubicacion.setZone(ubicacionDetails.getZone());
            final Ubicacion updatedUbicacion = ubicacionRepository.save(ubicacion);
            return new ResponseEntity(updatedUbicacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Ubicacion #" + ubicacionId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ubicacion/{id}")
    public ResponseEntity deleteUbicacion(
            @PathVariable(value = "id") Long ubicacionId) {
        if (ubicacionRepository.existsById(ubicacionId)) {
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionId).get();
            if (ubicacion.getUbicacionFurgons().size() > 0) {
                Set<UbicacionFurgon> ubFs = ubicacion.getUbicacionFurgons();
                for (UbicacionFurgon ub : ubFs) {
                    ub.getFurgon().setUbicacionFurgons(new HashSet());
                    furgonRepository.save(ub.getFurgon());
                    ubicacionFurgonRepository.delete(ub);
                }
            }
            ubicacionRepository.delete(ubicacion);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Ubicacion #" + ubicacionId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
