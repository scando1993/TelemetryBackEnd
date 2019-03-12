package net.pacificsoft.microservices.favorita.controllers.application;

import net.pacificsoft.springbootcrudrest.model.Provincia;
import net.pacificsoft.springbootcrudrest.model.Zona;
import net.pacificsoft.springbootcrudrest.repository.ProvinciaRepository;
import net.pacificsoft.springbootcrudrest.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class ProvinciaController {
    
	@Autowired
	private ProvinciaRepository provinciaRepository;
        
	@Autowired
	private ZonaRepository zonaRepository;
	@GetMapping("/provincia")
	public ResponseEntity getAllProvincias() {
                try{
                    return new ResponseEntity(provinciaRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                }  
	}

	@GetMapping("/provincia/{id}")
	public ResponseEntity getProvinciaById(
			@PathVariable(value = "id") Long provinciaId){
                if(provinciaRepository.exists(provinciaId)){
                    Provincia provincia = provinciaRepository.findOne(provinciaId);
                    return new ResponseEntity(provincia, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Provincia #" + provinciaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/provincia/{zonaid}")
	public ResponseEntity createProvincia(@PathVariable(value = "zonaid") Long zonaid,
                                 @Valid @RequestBody Provincia provincia) {
            try{
                if (zonaid==0){
                    Provincia p = provinciaRepository.save(provincia);
                    return new ResponseEntity(p, HttpStatus.CREATED);
                }
                else if(zonaRepository.exists(zonaid)){
                    Zona zona = zonaRepository.findOne(zonaid);
                    zona.getProvincias().add(provincia);
                    provincia.setZona(zona);
                    Provincia p = provinciaRepository.save(provincia);
                    zonaRepository.save(zona);
                    return new ResponseEntity(p, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Zona #" + zonaid + 
                            " does not exist, it's not possible create new Provincia", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Bodega", HttpStatus.NOT_FOUND);
                
            }
	}

        @PutMapping("/provincia/{id}}")
	public ResponseEntity updateProvincia(
			@PathVariable(value = "id") Long provinciaid,
			@Valid @RequestBody Provincia provinciaDetails){
            try{
                if(provinciaRepository.exists(provinciaid)) {
                    Provincia provincia = provinciaRepository.findOne(provinciaid);
                    provincia.setName(provinciaDetails.getName());
                    final Provincia updatedProvincia = provinciaRepository.save(provincia);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Provincia #" + provinciaid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update "
                        + "Provincia #" + provinciaid, HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/provincia/{id}")
	public ResponseEntity deleteProvincia(
                                @PathVariable(value = "id") Long provinciaid){
                if(provinciaRepository.exists(provinciaid)){
                    Provincia provincia = provinciaRepository.findOne(provinciaid);
                    provinciaRepository.delete(provincia);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Provincia #" + provinciaid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
