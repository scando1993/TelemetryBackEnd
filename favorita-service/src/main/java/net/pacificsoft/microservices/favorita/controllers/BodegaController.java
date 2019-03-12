package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.models.Ubicacion;
import net.pacificsoft.microservices.favorita.repository.BodegaRepository;
import net.pacificsoft.microservices.favorita.repository.UbicacionRepository;
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
@RequestMapping("/api")
public class BodegaController {
    
	@Autowired
	private BodegaRepository bodegaRepository;
        
        @Autowired
	private UbicacionRepository ubicacionRepository;
	
	@GetMapping("/bodega")
	public ResponseEntity getAllBodegas() {
                try{
                    List<Map<String, Object>> result = new ArrayList();
                    List<Bodega> bodegas = bodegaRepository.findAll();
                    JSONObject json;
                    for(Bodega b : bodegas){
                        json = new JSONObject();
                        json.put("id", b.getId());
                        json.put("name",b.getName());
                        json.put("zone",b.getUbication().getZone());
                        result.add(json.toMap());
                    }
                    return new ResponseEntity(result, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                }  
	}

	@GetMapping("/bodega/{id}")
	public ResponseEntity getBodegaById(
			@PathVariable(value = "id") Long bodegaId){
                if(bodegaRepository.existsById(bodegaId)){
                    Bodega bodega = bodegaRepository.findById(bodegaId).get();
                    return new ResponseEntity(bodega, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Bodega #" + bodegaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/bodega/{ubicacionid}")
	public ResponseEntity createBodega(@PathVariable(value = "ubicacionid") Long ubicacionid,
                                 @Valid @RequestBody Bodega bodega) {
                //for (ubicacionRepository.)
                if(ubicacionRepository.existsById(ubicacionid)){
                    Ubicacion ubicacion = ubicacionRepository.findById(ubicacionid).get();
                    ubicacion.getBodegas().add(bodega);
                    bodega.setUbication(ubicacion);
                    Bodega b = bodegaRepository.save(bodega);
                    ubicacionRepository.save(ubicacion);
                    return new ResponseEntity(b, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Ubicacion #" + ubicacionid + 
                            " does not exist, isn't possible create new Bodega", HttpStatus.NOT_FOUND);
                }
                
	}

        @PutMapping("/bodega/{id}/{ubicacionid}")
	public ResponseEntity updateBodega(
			@PathVariable(value = "id") Long bodegaId,
                        @PathVariable(value = "ubicacionid") Long ubicacionid,
			@Valid @RequestBody Bodega bodegaDetails){
                if(bodegaRepository.existsById(bodegaId) &&
                   ubicacionRepository.existsById(ubicacionid)) {
                    Bodega bodega = bodegaRepository.findById(bodegaId).get();
                    Ubicacion ubicacion = ubicacionRepository.findById(ubicacionid).get();
                    bodega.setName(bodegaDetails.getName());
                    bodega.setUbication(ubicacion);
                    final Bodega updatedBodega = bodegaRepository.save(bodega);
                    return new ResponseEntity(updatedBodega, HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Bodega #" + bodegaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/bodega/{id}")
	public ResponseEntity deleteBodega(
                                @PathVariable(value = "id") Long bodegaId){
		if(bodegaRepository.existsById(bodegaId)){
                    Bodega bodega = bodegaRepository.findById(bodegaId).get();
                    bodega.getUbication().getBodegas().remove(bodega);
                    ubicacionRepository.save(bodega.getUbication());
                    bodegaRepository.delete(bodega);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Bodega #" + bodegaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
