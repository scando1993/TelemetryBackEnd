package net.pacificsoft.microservices.favorita.controllers.application;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import net.pacificsoft.microservices.favorita.models.application.Locales;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
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
import net.pacificsoft.microservices.favorita.models.application.Formato;
import net.pacificsoft.microservices.favorita.repository.application.FormatoRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                    List<Formato> formatos = formatoRepository.findAll();
                
                    return new ResponseEntity(formatos, HttpStatus.OK);
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
                    Formato f = formatoRepository.findById(formatoId).get();
                
                    return new ResponseEntity(f, HttpStatus.OK);
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
                    Locales locales = localesRepository.findById(localesid).get();
                    if(locales.getFormato()==null){
                        locales.setFormato(formato);
                        formato.setLocales(locales);
                        Formato f = formatoRepository.save(formato);
                        localesRepository.save(locales);
                        
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
                    Locales locales = localesRepository.findById(localesid).get();
                    if(locales.getFormato()==null){
                        Formato formato = formatoRepository.findById(formatoid).get();
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
                    Formato formato = formatoRepository.findById(formatoId).get();
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


    @GetMapping("/getFormatoes")
    public ResponseEntity getFormatos(){
        try{
            List<Formato> formatoes = formatoRepository.findAll();
            List<Map<String, Object>> result = new ArrayList();
            JSONObject jFormato;
            for(Formato f: formatoes){
                jFormato = new JSONObject();
                Locales l = f.getLocales();
                jFormato.put("idFormato", f.getId());
                jFormato.put("nameFormato", f.getName());
                jFormato.put("code", f.getCode());
                if(l != null){
                    jFormato.put("idLocal", l.getId());
                    jFormato.put("family", l.getFamily());
                    jFormato.put("latitude", l.getLatitude());
                    jFormato.put("numLoc", l.getNumLoc());
                    jFormato.put("nameLocal", l.getName());
                    jFormato.put("length", l.getLength());
                }
                else{
                    jFormato.put("idLocal", "");
                    jFormato.put("family", "");
                    jFormato.put("latitude", "");
                    jFormato.put("numLoc", "");
                    jFormato.put("nameLocal", "");
                    jFormato.put("length", "");
                }
                result.add(jFormato.toMap());
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
