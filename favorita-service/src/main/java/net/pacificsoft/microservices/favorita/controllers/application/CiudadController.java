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
import net.pacificsoft.microservices.favorita.models.application.Ciudad;
import net.pacificsoft.microservices.favorita.models.application.Provincia;
import net.pacificsoft.microservices.favorita.repository.application.CiudadRepository;
import net.pacificsoft.microservices.favorita.repository.application.ProvinciaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class CiudadController {
    
	@Autowired
	private CiudadRepository ciudadRepository;
        
	@Autowired
	private ProvinciaRepository provinciaRepository;
        
	@GetMapping("/ciudad")
	public ResponseEntity getAllCiudad() {
                try{
                    return new ResponseEntity(ciudadRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                }  
	}

	@GetMapping("/ciudad/{id}")
	public ResponseEntity getCiudadById(
			@PathVariable(value = "id") Long ciudadid){
                if(ciudadRepository.existsById(ciudadid)){
                    Ciudad ciudad = ciudadRepository.findById(ciudadid).get();
                    return new ResponseEntity(ciudad, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Ciudad #" + ciudadid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/ciudad/{provinciaid}")
	public ResponseEntity createCiudad(@PathVariable(value = "provinciaid") Long provinciaid,
                                 @Valid @RequestBody Ciudad ciudad) {
            try{
                if(provinciaRepository.existsById(provinciaid)){
                    Provincia provincia = provinciaRepository.findById(provinciaid).get();
                    ciudad.setProvincia(provincia);
                    provincia.getCiudades().add(ciudad);
                    Ciudad c = ciudadRepository.save(ciudad);
                    provinciaRepository.save(provincia);
                    return new ResponseEntity(c, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Provincia #" + provinciaid + 
                            " does not exist, it's not possible create new Ciudad", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Ciudad", HttpStatus.NOT_FOUND);
                
            }
	}

        @PutMapping("/ciudad/{id}")
	public ResponseEntity updateCiudad(
			@PathVariable(value = "id") Long ciudadid,
			@Valid @RequestBody Ciudad ciudadDetails){
            try{
                if(ciudadRepository.existsById(ciudadid)) {
                    Ciudad ciudad = ciudadRepository.findById(ciudadid).get();
                    ciudad.setName(ciudadDetails.getName());
                    final Ciudad updatedCiudad = ciudadRepository.save(ciudad);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Ciudad #" + ciudadid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update "
                        + "Ciudad #" + ciudadid, HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/ciudad/{id}")
	public ResponseEntity deleteCiudad(
                                @PathVariable(value = "id") Long ciudadid){
                if(ciudadRepository.existsById(ciudadid)){
                    Ciudad ciudad = ciudadRepository.findById(ciudadid).get();
                    ciudadRepository.delete(ciudad);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Ciudad #" + ciudadid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
