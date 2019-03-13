package net.pacificsoft.microservices.favorita.controllers.application;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.repository.application.ProvinciaRepository;
import net.pacificsoft.microservices.favorita.repository.application.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.exception.ResourceNotFoundException;
import net.pacificsoft.microservices.favorita.models.application.Furgon;
import net.pacificsoft.microservices.favorita.models.application.Provincia;
import net.pacificsoft.microservices.favorita.models.application.Zona;

import net.pacificsoft.microservices.favorita.repository.*;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class ZonaController {
	
	@Autowired
	private ZonaRepository zonaRepository;

        @Autowired
	private ProvinciaRepository provinciaRepository;
        
	@GetMapping("/zona")
	public ResponseEntity getAllZonas() {
		try{
                    return new ResponseEntity(zonaRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/zona/{id}")
	public ResponseEntity getZonaById(
			@PathVariable(value = "id") Long zonaId){
		if(zonaRepository.existsById(zonaId)){
                    Zona zona = zonaRepository.findById(zonaId).get();
                    return new ResponseEntity(zona, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Furgon #" + zonaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/zona/{provincias}")
	public ResponseEntity createZona(@PathVariable(value = "provincias") Long[] provincias,
                                    @Valid @RequestBody Zona zona) {
                try{
                    for(Long l:provincias){
                        if(provinciaRepository.existsById(l)){
                            Provincia p = provinciaRepository.findById(l).get();
                            p.setZona(zona);
                            zona.getProvincias().add(p);
                            zonaRepository.save(zona);
                            provinciaRepository.save(p);
                        }                        
                    }
                    Zona z = zonaRepository.save(zona);
                    return new ResponseEntity(z, HttpStatus.CREATED);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("New zona not created.",
                            HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/zona/{id}/{provincias}")
	public ResponseEntity updateZona(
			@PathVariable(value = "id") Long zonaId,
                        @PathVariable(value = "provincias") Long[] provincias,
			@Valid @RequestBody Zona zonaDetails){
            try{
                if(zonaRepository.existsById(zonaId)){
                    Zona zona = zonaRepository.findById(zonaId).get();
                    for(Provincia p: zona.getProvincias()){
                        p.setZona(null);
                        provinciaRepository.save(p);
                    }
                    zona.setProvincias(new HashSet<>());
                    zona.setName(zonaDetails.getName());
                    for(Long l:provincias){
                        Provincia p = provinciaRepository.findById(l).get();
                        zona.getProvincias().add(p);
                        p.setZona(zona);
                        provinciaRepository.save(p);
                    }
                    final Zona updatedZona = zonaRepository.save(zona);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Zona #" + zonaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Zona #" + zonaId, HttpStatus.NOT_FOUND);
            }
	}
        
	@DeleteMapping("/zona/{id}")
	public ResponseEntity deleteZona(
			@PathVariable(value = "id") Long zonaId){
                if(zonaRepository.existsById(zonaId)){
                    Zona zona = zonaRepository.findById(zonaId).get();
                    if(zona.getProvincias().size()>0){
                        Set<Provincia> ps = zona.getProvincias();
                        for(Provincia p: ps){
                            p.setZona(null);
                            provinciaRepository.save(p);
                        }
                    }
                    zonaRepository.delete(zona);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Zona #" + zonaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
        
}
