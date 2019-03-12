package net.pacificsoft.microservices.favorita.controllers.application;


import net.pacificsoft.springbootcrudrest.model.Provincia;
import net.pacificsoft.springbootcrudrest.model.Zona;
import net.pacificsoft.springbootcrudrest.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

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
		if(zonaRepository.exists(zonaId)){
                    Zona zona = zonaRepository.findOne(zonaId);
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
                        if(provinciaRepository.exists(l)){
                            Provincia p = provinciaRepository.findOne(l);
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
                if(zonaRepository.exists(zonaId)){
                    Zona zona = zonaRepository.findOne(zonaId);
                    for(Provincia p: zona.getProvincias()){
                        p.setZona(null);
                        provinciaRepository.save(p);
                    }
                    zona.setProvincias(new HashSet<>());
                    zona.setName(zonaDetails.getName());
                    for(Long l:provincias){
                        Provincia p = provinciaRepository.findOne(l);
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
                if(zonaRepository.exists(zonaId)){
                    Zona zona = zonaRepository.findOne(zonaId);
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
