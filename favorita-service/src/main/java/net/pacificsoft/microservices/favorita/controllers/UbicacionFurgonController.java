package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Furgon;
import net.pacificsoft.microservices.favorita.models.Ubicacion;
import net.pacificsoft.microservices.favorita.models.UbicacionFurgon;
import net.pacificsoft.microservices.favorita.repository.FurgonRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionFurgonRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class UbicacionFurgonController {

    @Autowired
    private UbicacionFurgonRepository ubicacionFurgonRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private FurgonRepository furgonRepository;

    @GetMapping("/ubicacionFurgon")
    public ResponseEntity getAllUbicacionFurgons() {
        try {
            return new ResponseEntity(ubicacionFurgonRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/ubicacionFurgon/{id}")
    public ResponseEntity getUbicacionFUrgonById(
            @PathVariable(value = "id") Long ubFurgonId) {
        if (ubicacionFurgonRepository.existsById(ubFurgonId)) {
            UbicacionFurgon ubFurgon = ubicacionFurgonRepository.findById(ubFurgonId).get();
            return new ResponseEntity(ubFurgon, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("UbicacionFurgon #" + ubFurgonId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ubicacionFurgon/{ubicacionid}/{furgonid}")
    public ResponseEntity createUbicacionFurgon(@PathVariable(value = "ubicacionid") Long ubicacionid,
                                                @PathVariable(value = "furgonid") Long furgonid) {
        if (ubicacionRepository.existsById(ubicacionid) &&
                furgonRepository.existsById(furgonid)) {
            boolean ingresar = true;
            UbicacionFurgon ubFurgon = new UbicacionFurgon();
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionid).get();
            ubicacion.getUbicacionFurgons().add(ubFurgon);
            Furgon furgon = furgonRepository.findById(furgonid).get();
            Set<UbicacionFurgon> ubs = furgon.getUbicacionFurgons();
            for (UbicacionFurgon u : ubs) {
                if (u.getFurgon().getId() == furgon.getId() &&
                        u.getUbication().getId() == ubicacion.getId()) {
                    ingresar = false;
                }
            }
            if (ingresar) {
                furgon.getUbicacionFurgons().add(ubFurgon);
                ubFurgon.setFurgon(furgon);
                ubFurgon.setUbication(ubicacion);
                UbicacionFurgon ub = ubicacionFurgonRepository.save(ubFurgon);
                ubicacionRepository.save(ubicacion);
                furgonRepository.save(furgon);
                return new ResponseEntity(ub, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<String>("UbicacionFurgon is created", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<String>("Ubicacion #" + ubicacionid + " or furgon #" + furgonid +
                    " does not exist, isn't possible create new UbicacionFurgon", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/ubicacionFurgon/{id}/{ubicacionid}/{furgonid}")
    public ResponseEntity updateUbicacionFurgon(
            @PathVariable(value = "id") Long ubFurgonId,
            @PathVariable(value = "ubicacionid") Long ubicacionid,
            @PathVariable(value = "furgonid") Long furgonid) {
        if (ubicacionFurgonRepository.existsById(ubFurgonId)) {
            UbicacionFurgon ubFurgon = ubicacionFurgonRepository.findById(ubFurgonId).get();
            Ubicacion ub = ubicacionRepository.findById(ubicacionid).get();
            Furgon furgon = furgonRepository.findById(furgonid).get();
            ubFurgon.setFurgon(furgon);
            ubFurgon.setUbication(ub);
            final UbicacionFurgon updatedUbicacion = ubicacionFurgonRepository.save(ubFurgon);
            return new ResponseEntity(updatedUbicacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("UbicacionFurgon #" + ubFurgonId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/ubicacionFurgon/{id}")
    public ResponseEntity deleteUbicacionFurgon(
            @PathVariable(value = "id") Long ubFurgonId) {
        if (ubicacionFurgonRepository.existsById(ubFurgonId)) {
            UbicacionFurgon ubFurgon = ubicacionFurgonRepository.findById(ubFurgonId).get();
            ubFurgon.getFurgon().getUbicacionFurgons().remove(ubFurgon);
            ubFurgon.getUbication().getUbicacionFurgons().remove(ubFurgon);
            furgonRepository.save(ubFurgon.getFurgon());
            ubicacionRepository.save(ubFurgon.getUbication());
            ubicacionFurgonRepository.delete(ubFurgon);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("UbicacionFurgon #" + ubFurgonId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
