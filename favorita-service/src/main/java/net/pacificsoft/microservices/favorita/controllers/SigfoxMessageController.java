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
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.repository.BodegaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.Ciudad;
import net.pacificsoft.microservices.favorita.repository.CiudadRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import net.pacificsoft.microservices.favorita.repository.SigfoxMessageRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class SigfoxMessageController {
        /*
	@Autowired
	private SigfoxMessageRepository sigfoxRepository;
        
        @Autowired
	private RawSensorDataRepository rawSensorRepository;
	
	@GetMapping("/sigfoxMessage")
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

	@GetMapping("/sigfoxMessage/{id}")
	public ResponseEntity getBodegaById(
			@PathVariable(value = "id") Long bodegaId){
                if(bodegaRepository.existsById(bodegaId)){
                    Bodega b = bodegaRepository.findById(bodegaId);
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

	@PostMapping("/sigfoxMessage/{rawdataId}")
	public ResponseEntity createBodega(@PathVariable(value = "ciudadid") Long ciudadid,
                                 @Valid @RequestBody Bodega bodega) {
            try{
                if(ciudadRepository.existsById(ciudadid)){
                    Ciudad ciudad = ciudadRepository.findById(ciudadid);
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

        @PutMapping("/sigfoxMessage/{id}/{rawdataId}")
	public ResponseEntity updateBodega(
			@PathVariable(value = "id") Long bodegaId,
                        @PathVariable(value = "ciudadid") Long ciudadId,
			@Valid @RequestBody Bodega bodegaDetails){
            try{
                if(bodegaRepository.existsById(bodegaId) &&
                   ciudadRepository.existsById(ciudadId)) {
                    Bodega bodega = bodegaRepository.findById(bodegaId);
                    Ciudad ciudad = ciudadRepository.findById(ciudadId);
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

        @DeleteMapping("/sigfoxMessage/{id}")
	public ResponseEntity deleteBodega(
                                @PathVariable(value = "id") Long bodegaId){
                if(bodegaRepository.existsById(bodegaId)){
                    Bodega bodega = bodegaRepository.findById(bodegaId);
                    bodega.getCiudad().getBodegas().remove(bodega);
                    ciudadRepository.save(bodega.getCiudad());
                    bodegaRepository.delete(bodega);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Bodega #" + bodegaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}*/
}
