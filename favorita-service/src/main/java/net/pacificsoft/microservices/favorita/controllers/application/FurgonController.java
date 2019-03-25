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
import net.pacificsoft.microservices.favorita.models.application.Furgon;
import net.pacificsoft.microservices.favorita.repository.application.FurgonRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class FurgonController {
	
	@Autowired
	private FurgonRepository furgonRepository;

        @Autowired
	private RutaRepository rutaRepository;
        
	@GetMapping("/furgon")
	public ResponseEntity getAllFurgons() {
		try{

                    List<Furgon> furgones = furgonRepository.findAll();
                    return new ResponseEntity(furgones, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/furgon/{id}")
	public ResponseEntity getFurgonById(
			@PathVariable(value = "id") Long furgonId){
		if(furgonRepository.existsById(furgonId)){
                    Furgon f = furgonRepository.findById(furgonId).get();
                    
                    return new ResponseEntity(f, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Furgon #" + furgonId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/furgon")
	public ResponseEntity createFurgon(@Valid @RequestBody Furgon furgon) {
                try{
                    Furgon f = furgonRepository.save(furgon);

                    
                    return new ResponseEntity(f, HttpStatus.CREATED);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("New Furgon not created.",
                            HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/furgon/{id}")
	public ResponseEntity updateFurgon(
			@PathVariable(value = "id") Long furgonId,
			@Valid @RequestBody Furgon furgonDetails){
            try{
                if(furgonRepository.existsById(furgonId)){
                    Furgon furgon = furgonRepository.findById(furgonId).get();
                    furgon.setNumFurgon(furgonDetails.getNumFurgon());
                    furgon.setName(furgonDetails.getName());
                    final Furgon updatedFurgon = furgonRepository.save(furgon);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Furgon #" + furgonId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Furgon #" + furgonId, HttpStatus.NOT_FOUND);
            }
	}
        
	@DeleteMapping("/furgon/{id}")
	public ResponseEntity deleteFurgon(
			@PathVariable(value = "id") Long furgonId){
                if(furgonRepository.existsById(furgonId)){
                    Furgon furgon = furgonRepository.findById(furgonId).get();
                    if(furgon.getRutas().size()>0){
                        Set<Ruta> ubFs = furgon.getRutas();
                        for(Ruta r:ubFs){
                            r.setFurgon(null);
                            rutaRepository.save(r);
                        }
                    }
                    furgonRepository.delete(furgon);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Furgon #" + furgonId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
