package net.pacificsoft.microservices.favorita.controllers.application;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.application.Locales;
import net.pacificsoft.microservices.favorita.models.application.Provincia;
import net.pacificsoft.microservices.favorita.models.application.Zona;
import net.pacificsoft.microservices.favorita.repository.application.LocalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.application.Ciudad;
import net.pacificsoft.microservices.favorita.repository.application.CiudadRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class LocalesController {
	
	@Autowired
	private LocalesRepository localesRepository;
        @Autowired
	private CiudadRepository ciudadRepository;

	@GetMapping("/locales")
	public ResponseEntity getAllLocales() {
		try{
                    List<Locales> locales = localesRepository.findAll();                    
                    return new ResponseEntity(locales, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/locales/{id}")
	public ResponseEntity getLocalesById(
			@PathVariable(value = "id") Long localesId){
		if(localesRepository.existsById(localesId)){
                    Locales l = localesRepository.findById(localesId).get();
                    
                    return new ResponseEntity(l, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Locales #" + localesId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/locales/{ciudadid}")
	public ResponseEntity createLocales(@PathVariable(value = "ciudadid") Long ciudadid,
                            @Valid @RequestBody Locales locales) {
            try{
                if(ciudadRepository.existsById(ciudadid)){
                    Ciudad ciudad = ciudadRepository.findById(ciudadid).get();
                    ciudad.getLocales().add(locales);
                    locales.setCiudad(ciudad);
                    Locales l = localesRepository.save(locales);
                    ciudadRepository.save(ciudad);
                    
                    return new ResponseEntity(l, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Ciudad #" + ciudadid + 
                            " does not exist, isn't possible create new Local", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible create new Local", HttpStatus.NOT_FOUND);
            }
	}

	@PutMapping("/locales/{id}/{ciudadid}")
	public ResponseEntity updateLocales(
			@PathVariable(value = "id") Long localesId,
                        @PathVariable(value = "ciudadid") Long ciudadId,
			@Valid @RequestBody Locales localesDetails){
            try{
                if(localesRepository.existsById(localesId) &&
                   ciudadRepository.existsById(ciudadId)){
                    Locales locales = localesRepository.findById(localesId).get();
                    Ciudad ciudad = ciudadRepository.findById(ciudadId).get();
                    locales.setLatitude(localesDetails.getLatitude());
                    locales.setLength(localesDetails.getLength());
                    locales.setName(localesDetails.getName());
                    locales.setNumLoc(localesDetails.getNumLoc());
                    locales.setFamily(localesDetails.getFamily());
                    locales.setCiudad(ciudad);
                    ciudad.getLocales().add(locales);
                    final Locales updatedLocales = localesRepository.save(locales);
                    ciudadRepository.save(ciudad);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Locales #" + localesId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Locales #" + localesId, HttpStatus.NOT_FOUND);
            }
	}

	@DeleteMapping("/locales/{id}")
	public ResponseEntity deleteLocales(
			@PathVariable(value = "id") Long localesId){
                if(localesRepository.existsById(localesId)){
                    Locales locales = localesRepository.findById(localesId).get();
                    locales.getCiudad().getLocales().remove(locales);
                    ciudadRepository.save(locales.getCiudad());
                    localesRepository.delete(locales);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Locales #" + localesId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

    @GetMapping("/getLocales")
    public ResponseEntity getLocales(){
        try{
            List<Locales> localeses = localesRepository.findAll();
            List<Map<String, Object>> result = new ArrayList();
            JSONObject jLocal;
            for(Locales l: localeses){
                jLocal = new JSONObject();
                Ciudad c = l.getCiudad();
                Provincia p = c.getProvincia();
                Zona z = p.getZona();
                jLocal.put("idLocal", l.getId());
                jLocal.put("family", l.getFamily());
                jLocal.put("latitude", l.getLatitude());
                jLocal.put("numLoc", l.getNumLoc());
                jLocal.put("nameLocal", l.getName());
                jLocal.put("length", l.getLength());

                jLocal.put("idCiudad", c.getId());
                jLocal.put("nameCiudad", c.getName());

                jLocal.put("idProvincia", p.getId());
                jLocal.put("nameProvincia", p.getName());

                jLocal.put("idZona", z.getId());
                jLocal.put("nameZona", z.getName());

                result.add(jLocal.toMap());
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
