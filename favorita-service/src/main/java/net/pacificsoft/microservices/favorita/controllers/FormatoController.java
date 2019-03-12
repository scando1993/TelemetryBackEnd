package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Formato;
import net.pacificsoft.microservices.favorita.models.Ubicacion;
import net.pacificsoft.microservices.favorita.repository.FormatoRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class FormatoController {

    @Autowired
    private FormatoRepository formatoRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;


    @GetMapping("/formato")
    public ResponseEntity getAllFormatos() {
        try {
            return new ResponseEntity(formatoRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/formato/{id}")
    public ResponseEntity getFormatoById(
            @PathVariable(value = "id") Long formatoId) {
        if (formatoRepository.existsById(formatoId)) {
            Formato formato = formatoRepository.findById(formatoId).get();
            return new ResponseEntity(formato, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Formato #" + formatoId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/formato/{ubicacionid}")
    public ResponseEntity createFormato(@PathVariable(value = "ubicacionid") Long ubicacionid,
                                        @Valid @RequestBody Formato formato) {
        if (ubicacionRepository.existsById(ubicacionid)) {
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionid).get();
            ubicacion.getFormatos().add(formato);
            formato.setUbication(ubicacion);
            Formato f = formatoRepository.save(formato);
            ubicacionRepository.save(ubicacion);
            return new ResponseEntity(f, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Ubicacion #" + ubicacionid +
                    " does not exist, isn't possible create new Formato", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/formato/{id}/{ubicacionid}")
    public ResponseEntity updateFormato(
            @PathVariable(value = "id") Long formatoid,
            @PathVariable(value = "ubicacionid") Long ubicacionid,
            @Valid @RequestBody Formato formatoDetails) {
        if (formatoRepository.existsById(formatoid) &&
                ubicacionRepository.existsById(ubicacionid)) {
            Formato formato = formatoRepository.findById(formatoid).get();
            Ubicacion ubicacion = ubicacionRepository.findById(ubicacionid).get();
            formato.setName(formatoDetails.getName());
            formato.setRuta(formatoDetails.getRuta());
            formato.setUbication(ubicacion);
            final Formato updatedFormato = formatoRepository.save(formato);
            return new ResponseEntity(updatedFormato, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Formato #" + formatoid +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/formato/{id}")
    public ResponseEntity deleteFormato(
            @PathVariable(value = "id") Long formatoId) {
        if (formatoRepository.existsById(formatoId)) {
            Formato formato = formatoRepository.findById(formatoId).get();
            formato.getUbication().getFormatos().remove(formato);
            ubicacionRepository.save(formato.getUbication());
            formatoRepository.delete(formato);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Formato #" + formatoId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
