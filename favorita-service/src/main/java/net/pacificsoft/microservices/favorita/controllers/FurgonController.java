package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Furgon;
import net.pacificsoft.microservices.favorita.models.Ubicacion;
import net.pacificsoft.microservices.favorita.models.UbicacionFurgon;
import net.pacificsoft.microservices.favorita.repository.FurgonRepository;
import net.pacificsoft.microservices.favorita.repository.RutaRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionFurgonRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class FurgonController {

    @Autowired
    private FurgonRepository furgonRepository;

    @Autowired
    private UbicacionFurgonRepository ubicacionFurgonRepository;

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @GetMapping("/furgon")
    public ResponseEntity getAllFurgons() {
        try {
            List<Map<String, Object>> result = new ArrayList();
            Set<Map<String, Object>> ubs;
            JSONObject json;
            JSONObject resp;
            List<Furgon> furgones = furgonRepository.findAll();
            List<Ubicacion> ubicaciones;
            for (Furgon f : furgones) {
                json = new JSONObject();
                ubs = new HashSet();
                ubicaciones = new ArrayList();
                json.put("id", f.getId());
                json.put("numFurgon", f.getNumFurgon());
                json.put("name", f.getName());
                Set<UbicacionFurgon> ubFurgones = f.getUbicacionFurgons();
                for (UbicacionFurgon ub : ubFurgones) {
                    resp = new JSONObject();
                    resp.put("id", ub.getUbication().getId());
                    resp.put("zone", ub.getUbication().getZone());
                    resp.put("regional", ub.getUbication().getRegional());
                    resp.put("province", ub.getUbication().getProvince());
                    resp.put("city", ub.getUbication().getCity());
                    ubs.add(resp.toMap());
                }
                json.put("Ubicaciones", ubs.toArray());
                result.add(json.toMap());
            }
            return new ResponseEntity(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/furgon/{id}")
    public ResponseEntity getFurgonById(
            @PathVariable(value = "id") Long furgonId) {
        if (furgonRepository.existsById(furgonId)) {
            Furgon furgon = furgonRepository.findById(furgonId).get();
            return new ResponseEntity(furgon, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Furgon #" + furgonId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/furgon")
    public ResponseEntity createFurgon(@Valid @RequestBody Furgon furgon) {
        try {
            Furgon f = furgonRepository.save(furgon);
            JSONObject json = new JSONObject();
            json.put("id", f.getId());
            json.put("numFurgon", f.getNumFurgon());
            json.put("name", f.getName());
            return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("New Furgon not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/furgon/{id}")
    public ResponseEntity updateFurgon(
            @PathVariable(value = "id") Long furgonId,
            @Valid @RequestBody Furgon furgonDetails) {
        if (furgonRepository.existsById(furgonId)) {
            Furgon furgon = furgonRepository.findById(furgonId).get();
            furgon.setNumFurgon(furgonDetails.getNumFurgon());
            furgon.setName(furgonDetails.getName());
            furgon.setRuta(furgonDetails.getRuta());
            furgon.setUbicacionFurgons(furgonDetails.getUbicacionFurgons());
            final Furgon updatedFurgon = furgonRepository.save(furgon);
            return new ResponseEntity(updatedFurgon, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Furgon #" + furgonId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/furgon/{id}")
    public ResponseEntity deleteFurgon(
            @PathVariable(value = "id") Long furgonId) {
        if (furgonRepository.existsById(furgonId)) {
            Furgon furgon = furgonRepository.findById(furgonId).get();
            if (furgon.getUbicacionFurgons().size() > 0) {
                Set<UbicacionFurgon> ubFs = furgon.getUbicacionFurgons();
                for (UbicacionFurgon ub : ubFs) {
                    ub.getUbication().setUbicacionFurgons(new HashSet());
                    furgonRepository.save(ub.getFurgon());
                    ubicacionFurgonRepository.delete(ub);
                }
            }
            if (furgon.getRuta() != null) {
                furgon.getRuta().setFurgon(null);
                rutaRepository.save(furgon.getRuta());
            }
            furgonRepository.delete(furgon);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Furgon #" + furgonId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
