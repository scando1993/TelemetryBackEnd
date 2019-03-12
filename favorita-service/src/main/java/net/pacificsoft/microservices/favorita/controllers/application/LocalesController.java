package net.pacificsoft.microservices.favorita.controllers.application;

import net.pacificsoft.springbootcrudrest.model.Ciudad;
import net.pacificsoft.springbootcrudrest.model.Locales;
import net.pacificsoft.springbootcrudrest.repository.CiudadRepository;
import net.pacificsoft.springbootcrudrest.repository.LocalesRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
		if(localesRepository.exists(localesId)){
                    Locales l = localesRepository.findOne(localesId);
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
                if(ciudadRepository.exists(ciudadid)){
                    Ciudad ciudad = ciudadRepository.findOne(ciudadid);
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
                if(localesRepository.exists(localesId) &&
                   ciudadRepository.exists(ciudadId)){
                    Locales locales = localesRepository.findOne(localesId);
                    Ciudad ciudad = ciudadRepository.findOne(ciudadId);
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
                if(localesRepository.exists(localesId)){
                    Locales locales = localesRepository.findOne(localesId);
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
