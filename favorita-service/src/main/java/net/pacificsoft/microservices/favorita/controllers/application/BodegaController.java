package net.pacificsoft.microservices.favorita.controllers.application;

import net.pacificsoft.microservices.favorita.repository.application.BodegaRepository;
import net.pacificsoft.microservices.favorita.repository.application.CiudadRepository;
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
public class BodegaController {
    
	@Autowired
	private BodegaRepository bodegaRepository;
        
        @Autowired
	private CiudadRepository ciudadRepository;
	
	@GetMapping("/bodega")
	public ResponseEntity getAllBodegas() {
                try{
                    List<Map<String, Object>> result = new ArrayList();
                    List<Bodega> bodegas = bodegaRepository.findAll();
                    JSONObject json;
                    for(Bodega b : bodegas){
                        json = new JSONObject();
                        Ciudad c = b.getCiudad();
                        json.put("id", b.getId());
                        json.put("name",b.getName());
                        json.put("cityName", c.getName());
                        json.put("provinceName", c.getProvincia().getName());
                        if(c.getProvincia().getZona()!=null)
                            json.put("zoneName", c.getProvincia().getZona().getName());
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
                if(bodegaRepository.exists(bodegaId)){
                    Bodega b = bodegaRepository.findOne(bodegaId);
                    JSONObject json = new JSONObject();  
                    Ciudad c = b.getCiudad();
                    json.put("id", b.getId());
                    json.put("name",b.getName());
                    json.put("cityName", c.getName());
                    json.put("provinceName", c.getProvincia().getName());
                    if(c.getProvincia().getZona()!=null)
                        json.put("zoneName", c.getProvincia().getZona().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Bodega #" + bodegaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/bodega/{ciudadid}")
	public ResponseEntity createBodega(@PathVariable(value = "ciudadid") Long ciudadid,
                                 @Valid @RequestBody Bodega bodega) {
            try{
                if(ciudadRepository.exists(ciudadid)){
                    Ciudad ciudad = ciudadRepository.findOne(ciudadid);
                    ciudad.getBodegas().add(bodega);
                    bodega.setCiudad(ciudad);
                    Bodega b = bodegaRepository.save(bodega);
                    ciudadRepository.save(ciudad);
                    JSONObject json = new JSONObject();
                    json.put("id", b.getId());
                    json.put("name",b.getName());
                    json.put("cityName", b.getCiudad().getName());
                    json.put("provinceName", b.getCiudad().getProvincia().getName());
                    if(b.getCiudad().getProvincia().getZona()!=null)
                        json.put("zoneName", b.getCiudad().getProvincia().getZona().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("Ciudad #" + ciudadid + 
                            " does not exist, it's not possible create new Bodega", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Bodega", HttpStatus.NOT_FOUND);
                
            }
	}

        @PutMapping("/bodega/{id}/{ciudadid}")
	public ResponseEntity updateBodega(
			@PathVariable(value = "id") Long bodegaId,
                        @PathVariable(value = "ciudadid") Long ciudadId,
			@Valid @RequestBody Bodega bodegaDetails){
            try{
                if(bodegaRepository.exists(bodegaId) &&
                   ciudadRepository.exists(ciudadId)) {
                    Bodega bodega = bodegaRepository.findOne(bodegaId);
                    Ciudad ciudad = ciudadRepository.findOne(ciudadId);
                    bodega.setName(bodegaDetails.getName());
                    bodega.setCiudad(ciudad);
                    ciudad.getBodegas().add(bodega);
                    final Bodega updatedBodega = bodegaRepository.save(bodega);                    
                    ciudadRepository.save(ciudad);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Bodega #" + bodegaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update "
                        + "Bodega #" + bodegaId, HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/bodega/{id}")
	public ResponseEntity deleteBodega(
                                @PathVariable(value = "id") Long bodegaId){
                if(bodegaRepository.exists(bodegaId)){
                    Bodega bodega = bodegaRepository.findOne(bodegaId);
                    bodega.getCiudad().getBodegas().remove(bodega);
                    ciudadRepository.save(bodega.getCiudad());
                    bodegaRepository.delete(bodega);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Bodega #" + bodegaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
