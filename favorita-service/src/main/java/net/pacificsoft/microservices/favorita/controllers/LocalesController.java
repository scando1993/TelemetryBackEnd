package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Locales;
import net.pacificsoft.microservices.favorita.models.Ubicacion;
import net.pacificsoft.microservices.favorita.repository.LocalesRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class LocalesController {

    @Autowired
    private LocalesRepository localesRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @GetMapping("/locales")
    public ResponseEntity getAllLocales() {
        try {
            return new ResponseEntity(localesRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/locales/{id}")
    public ResponseEntity getLocalesById(
            @PathVariable(value = "id") Long localesId) {
        if (localesRepository.existsById(localesId)) {
            Locales locales = localesRepository.findById(localesId).get();
            return new ResponseEntity(locales, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Locales #" + localesId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/locales/{ubicacionid}")
    public ResponseEntity createLocales(@PathVariable(value = "ubicacionid") Long ubicacionid,
                                        @Valid @RequestBody Locales locales) {
        if (ubicacionRepository.existsById(ubicacionid)) {
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionid).get();
            ubicacion.getLocales().add(locales);
            locales.setUbication(ubicacion);
            Locales l = localesRepository.save(locales);
            ubicacionRepository.save(ubicacion);
            return new ResponseEntity(l, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Ubicacion #" + ubicacionid +
                    " does not exist, isn't possible create new Formato", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/locales/{id}/{ubicacionid}")
    public ResponseEntity updateLocales(
            @PathVariable(value = "id") Long localesId,
            @PathVariable(value = "ubicacionid") Long ubicacionid,
            @Valid @RequestBody Locales localesDetails) {
        if (localesRepository.existsById(localesId) &&
                ubicacionRepository.existsById(ubicacionid)) {
            Locales locales = localesRepository.findById(localesId).get();
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionid).get();
            locales.setLatitude(localesDetails.getLatitude());
            locales.setLength(localesDetails.getLength());
            locales.setName(localesDetails.getName());
            locales.setPlace(localesDetails.getPlace());
            locales.setNumLoc(localesDetails.getNumLoc());
            locales.setUbication(ubicacion);
            final Locales updatedLocales = localesRepository.save(locales);
            return new ResponseEntity(updatedLocales, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Locales #" + localesId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/locales/{id}")
    public ResponseEntity deleteLocales(
            @PathVariable(value = "id") Long localesId) {
        if (localesRepository.existsById(localesId)) {
            Locales locales = localesRepository.findById(localesId).get();
            locales.getUbication().getLocales().remove(locales);
            ubicacionRepository.save(locales.getUbication());
            localesRepository.delete(locales);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Locales #" + localesId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
