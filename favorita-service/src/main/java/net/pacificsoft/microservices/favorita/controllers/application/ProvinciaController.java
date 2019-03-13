package net.pacificsoft.microservices.favorita.controllers.application;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.pacificsoft.microservices.favorita.models.application.Provincia;
import net.pacificsoft.microservices.favorita.models.application.Zona;
import net.pacificsoft.microservices.favorita.repository.application.ProvinciaRepository;
import net.pacificsoft.microservices.favorita.repository.application.ZonaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

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
                if(provinciaRepository.existsById(provinciaId)){
                    Provincia provincia = provinciaRepository.findById(provinciaId).get();
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
                else if(zonaRepository.existsById(zonaid)){
                    Zona zona = zonaRepository.findById(zonaid).get();
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
                if(provinciaRepository.existsById(provinciaid)) {
                    Provincia provincia = provinciaRepository.findById(provinciaid).get();
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
                if(provinciaRepository.existsById(provinciaid)){
                    Provincia provincia = provinciaRepository.findById(provinciaid).get();
                    provinciaRepository.delete(provincia);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Provincia #" + provinciaid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
