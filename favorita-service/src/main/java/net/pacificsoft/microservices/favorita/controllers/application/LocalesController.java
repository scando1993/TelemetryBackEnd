package net.pacificsoft.microservices.favorita.controllers.application;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.application.Locales;
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
                    List<Map<String, Object>> result = new ArrayList();
                    List<Locales> locales = localesRepository.findAll();
                    JSONObject json;
                    for(Locales l : locales){
                        json = new JSONObject();
                        Ciudad c = l.getCiudad();
                        json.put("id", l.getId());
                        json.put("numLoc",l.getNumLoc());
                        json.put("name",l.getName());
                        json.put("length",l.getLength());
                        json.put("latitude",l.getLatitude());
                        json.put("cityName", c.getName());
                        json.put("family",l.getFamily());
                        json.put("provinceName",c.getProvincia().getName());
                        if(c.getProvincia().getZona()!=null)
                            json.put("zoneName",c.getProvincia().getZona().getName());
                        result.add(json.toMap());
                    }
                    return new ResponseEntity(result, HttpStatus.OK);
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
                    JSONObject json = new JSONObject();
                    Ciudad c = l.getCiudad();
                    json.put("id", l.getId());
                    json.put("numLoc",l.getNumLoc());
                    json.put("name",l.getName());
                    json.put("length",l.getLength());
                    json.put("latitude",l.getLatitude());
                    json.put("cityName", c.getName());
                    json.put("family",l.getFamily());
                    json.put("provinceName",c.getProvincia().getName());
                    if(c.getProvincia().getZona()!=null)
                        json.put("zoneName",c.getProvincia().getZona().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
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
                    JSONObject json = new JSONObject();
                    Ciudad c = l.getCiudad();
                    json.put("id", l.getId());
                    json.put("numLoc",l.getNumLoc());
                    json.put("name",l.getName());
                    json.put("length",l.getLength());
                    json.put("latitude",l.getLatitude());
                    json.put("cityName", c.getName());
                    json.put("family",l.getFamily());
                    json.put("provinceName",c.getProvincia().getName());
                    if(c.getProvincia().getZona()!=null)
                        json.put("zoneName",c.getProvincia().getZona().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
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
}
