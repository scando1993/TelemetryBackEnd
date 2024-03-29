package net.pacificsoft.microservices.favorita.controllers.application;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.application.Provincia;
import net.pacificsoft.microservices.favorita.models.application.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.models.application.Bodega;
import net.pacificsoft.microservices.favorita.repository.application.BodegaRepository;
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
public class BodegaController {
    
	@Autowired
	private BodegaRepository bodegaRepository;
        
        @Autowired
	private CiudadRepository ciudadRepository;
	
	@GetMapping("/bodega")
	public ResponseEntity getAllBodegas() {
                try{
                    
                    List<Bodega> bodegas = bodegaRepository.findAll();
                    
                    return new ResponseEntity(bodegas, HttpStatus.OK);
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
                    Bodega b = bodegaRepository.findById(bodegaId).get();
                    
                    return new ResponseEntity(b, HttpStatus.OK);
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
                if(ciudadRepository.existsById(ciudadid)){
                    Ciudad ciudad = ciudadRepository.findById(ciudadid).get();
                    ciudad.getBodegas().add(bodega);
                    bodega.setCiudad(ciudad);
                    Bodega b = bodegaRepository.save(bodega);
                    ciudadRepository.save(ciudad);
                    return new ResponseEntity(b, HttpStatus.CREATED);
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
                if(bodegaRepository.existsById(bodegaId) &&
                   ciudadRepository.existsById(ciudadId)) {
                    Bodega bodega = bodegaRepository.findById(bodegaId).get();
                    Ciudad ciudad = ciudadRepository.findById(ciudadId).get();
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
                if(bodegaRepository.existsById(bodegaId)){
                    Bodega bodega = bodegaRepository.findById(bodegaId).get();
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

    @GetMapping("/getBodegas")
    public ResponseEntity getLocales(){
        try{
            List<Bodega> bodegas = bodegaRepository.findAll();
            List<Map<String, Object>> result = new ArrayList();
            JSONObject jBodega;
            for(Bodega l: bodegas){
                jBodega = new JSONObject();
                Ciudad c = l.getCiudad();
                Provincia p = c.getProvincia();
                Zona z = p.getZona();
                jBodega.put("idBodega", l.getId());
                jBodega.put("nameBodega", l.getName());
                
                if(c != null){
                    jBodega.put("idCiudad", c.getId());
                    jBodega.put("nameCiudad", c.getName());
                }
                else{
                    jBodega.put("idCiudad", "");
                    jBodega.put("nameCiudad", "");
                }
                if(p != null){
                    jBodega.put("idProvincia", p.getId());
                    jBodega.put("nameProvincia", p.getName());
                }
                else{
                    jBodega.put("idProvincia", "");
                    jBodega.put("nameProvincia", "");
                }
                if(z != null){
                    jBodega.put("idZona", z.getId());
                    jBodega.put("nameZona", z.getName());
                }
                else{
                    jBodega.put("idZona", "");
                    jBodega.put("nameZona", "");
                }
                result.add(jBodega.toMap());
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
