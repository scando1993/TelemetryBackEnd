package net.pacificsoft.microservices.favorita.controllers;

import javax.validation.Valid;
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
import net.pacificsoft.microservices.favorita.models.Formato;
import net.pacificsoft.microservices.favorita.repository.FormatoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.Locales;
import net.pacificsoft.microservices.favorita.repository.LocalesRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class FormatoController {
	
	@Autowired
	private FormatoRepository formatoRepository;
        
        @Autowired
	private LocalesRepository localesRepository;
	@GetMapping("/formato")
	public ResponseEntity getAllFormatos() {
		try{
                    List<Map<String, Object>> result = new ArrayList();
                    List<Formato> formatos = formatoRepository.findAll();
                    JSONObject json;
                    for(Formato f : formatos){
                        json = new JSONObject();
                        json.put("id", f.getId());
                        json.put("name",f.getName());
                        json.put("code",f.getCode());
                        json.put("localName",f.getLocales().getName());
                        result.add(json.toMap());
                    }
                    return new ResponseEntity(result, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/formato/{id}")
	public ResponseEntity getFormatoById(
			@PathVariable(value = "id") Long formatoId){
		if(formatoRepository.existsById(formatoId)){
                    Formato f = formatoRepository.findById(formatoId);
                    JSONObject json = new JSONObject();
                    json.put("id", f.getId());
                    json.put("name",f.getName());
                    json.put("code",f.getCode());
                    json.put("localName",f.getLocales().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Formato #" + formatoId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/formato/{localesid}")
	public ResponseEntity createFormato(@PathVariable(value = "localesid") Long localesid,
                                  @Valid @RequestBody Formato formato) {
            try{
                if(localesRepository.existsById(localesid)){
                    Locales locales = localesRepository.findById(localesid);
                    if(locales.getFormato()==null){
                        locales.setFormato(formato);
                        formato.setLocales(locales);
                        Formato f = formatoRepository.save(formato);
                        localesRepository.save(locales);
                        JSONObject json = new JSONObject();
                        json.put("id", f.getId());
                        json.put("name",f.getName());
                        json.put("code",f.getCode());
                        json.put("localName",f.getLocales().getName());
                        return new ResponseEntity(f, HttpStatus.CREATED);}
                    else{
                        return new ResponseEntity<String>("It's not possible create new Formato", HttpStatus.NOT_FOUND);
                    }
                }
		else{
                    return new ResponseEntity<String>("Local #" + localesid + 
                            " does not exist, it isn't possible create new Formato", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible create new Formato", HttpStatus.NOT_FOUND);
            }
	}

	@PutMapping("/formato/{id}/{localid}")
	public ResponseEntity updateFormato(
			@PathVariable(value = "id") Long formatoid,
                        @PathVariable(value = "localid") Long localesid,
			@Valid @RequestBody Formato formatoDetails){
            try{
                if(formatoRepository.existsById(formatoid) &&
                   localesRepository.existsById(localesid)){
                    Locales locales = localesRepository.findById(localesid);
                    if(locales.getFormato()==null){
                        Formato formato = formatoRepository.findById(formatoid);
                        formato.setName(formatoDetails.getName());
                        formato.setCode(formatoDetails.getCode());
                        formato.setLocales(locales);
                        locales.setFormato(formato);
                        localesRepository.save(locales);
                        final Formato updatedFormato = formatoRepository.save(formato);
                    return new ResponseEntity(HttpStatus.OK);}
                    else{
                        return new ResponseEntity<String>("It's not possible update Formato #" + formatoid, HttpStatus.NOT_FOUND);
                    }
                }
		else{
                    return new ResponseEntity<String>("Formato #" + formatoid + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Formato #" + formatoid, HttpStatus.NOT_FOUND);
            }
	}

	@DeleteMapping("/formato/{id}")
	public ResponseEntity deleteFormato(
			@PathVariable(value = "id") Long formatoId){
                if(formatoRepository.existsById(formatoId)){
                    Formato formato = formatoRepository.findById(formatoId);
                    formato.getLocales().setFormato(null);
                    localesRepository.save(formato.getLocales());
                    formatoRepository.delete(formato);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Formato #" + formatoId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
