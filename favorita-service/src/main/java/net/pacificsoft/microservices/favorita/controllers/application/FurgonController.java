package net.pacificsoft.springbootcrudrest.controller;


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
import net.pacificsoft.springbootcrudrest.model.Furgon;
import net.pacificsoft.springbootcrudrest.repository.FurgonRepository;
import net.pacificsoft.springbootcrudrest.repository.RutaRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.pacificsoft.springbootcrudrest.model.Ruta;
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
                    List<Map<String, Object>> result = new ArrayList();
                    Set<Map<String, Object>> ubs;
                    JSONObject json;
                    JSONObject resp;
                    List<Furgon> furgones = furgonRepository.findAll();
                    for (Furgon f : furgones){
                        json = new JSONObject();
                        ubs = new HashSet();
                        json.put("id", f.getId());
                        json.put("numFurgon",f.getNumFurgon());
                        json.put("name",f.getName());
                        /*Set<Ruta> rutas = f.getRutas();
                        for (Ruta r: rutas){
                            resp = new JSONObject();
                            resp.put("id", r.getId());
                            if(r.getLocalInicio().getCiudad().getProvincia().getZona()!=null)
                                resp.put("nameStartzone", r.getLocalInicio().
                                    getCiudad().getProvincia().getZona().getName());
                            resp.put("nameStartProvince", r.getLocalInicio().
                                    getCiudad().getProvincia().getName());
                            resp.put("nameStartCity", r.getLocalInicio().
                                    getCiudad().getName());
                            if(r.getLocalFin().getCiudad().getProvincia().getZona()!=null)
                                resp.put("nameEndzone", r.getLocalFin().
                                    getCiudad().getProvincia().getZona().getName());
                            resp.put("nameEndProvince", r.getLocalFin().
                                    getCiudad().getProvincia().getName());
                            resp.put("nameEndCity", r.getLocalFin().
                                    getCiudad().getName());
                            ubs.add(resp.toMap());
                        }
                        json.put("Rutas", ubs.toArray());*/
                        result.add(json.toMap());
                    }
                    return new ResponseEntity(result, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/furgon/{id}")
	public ResponseEntity getFurgonById(
			@PathVariable(value = "id") Long furgonId){
		if(furgonRepository.exists(furgonId)){
                    Furgon f = furgonRepository.findOne(furgonId);
                    JSONObject json = new JSONObject();
                    JSONObject resp = new JSONObject();
                    Set<Map<String, Object>> ubs = new HashSet();
                    json.put("id", f.getId());
                    json.put("numFurgon",f.getNumFurgon());
                    json.put("name",f.getName());
                    /*Set<Ruta> rutas = f.getRutas();
                    for (Ruta r: rutas){
                        resp = new JSONObject();
                        resp.put("id", r.getId());
                        if(r.getLocalInicio().getCiudad().getProvincia().getZona()!=null)
                            resp.put("nameStartzone", r.getLocalInicio().
                                getCiudad().getProvincia().getZona().getName());
                        resp.put("nameStartProvince", r.getLocalInicio().
                                getCiudad().getProvincia().getName());
                        resp.put("nameStartCity", r.getLocalInicio().
                                getCiudad().getName());
                        if(r.getLocalFin().getCiudad().getProvincia().getZona()!=null)
                            resp.put("nameEndzone", r.getLocalFin().
                                getCiudad().getProvincia().getZona().getName());
                        resp.put("nameEndProvince", r.getLocalFin().
                                getCiudad().getProvincia().getName());
                        resp.put("nameEndCity", r.getLocalFin().
                                getCiudad().getName());
                        ubs.add(resp.toMap());
                    }
                    json.put("Rutas", ubs.toArray());*/
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
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
                    JSONObject json = new JSONObject();
                    JSONObject resp = new JSONObject();
                    Set<Map<String, Object>> ubs = new HashSet();
                    json.put("id", f.getId());
                    json.put("numFurgon", f.getNumFurgon());
                    json.put("name", f.getName());
                    /*Set<Ruta> rutas = f.getRutas();
                    for (Ruta r: rutas){
                        resp = new JSONObject();
                        resp.put("id", r.getId());
                        if(r.getLocalInicio().getCiudad().getProvincia().getZona()!=null)
                            resp.put("nameStartzone", r.getLocalInicio().
                                getCiudad().getProvincia().getZona().getName());
                        resp.put("nameStartProvince", r.getLocalInicio().
                                getCiudad().getProvincia().getName());
                        resp.put("nameStartCity", r.getLocalInicio().
                                getCiudad().getName());
                        if(r.getLocalFin().getCiudad().getProvincia().getZona()!=null)
                            resp.put("nameEndzone", r.getLocalFin().
                                getCiudad().getProvincia().getZona().getName());
                        resp.put("nameEndProvince", r.getLocalFin().
                                getCiudad().getProvincia().getName());
                        resp.put("nameEndCity", r.getLocalFin().
                                getCiudad().getName());
                        ubs.add(resp.toMap());
                    }
                    json.put("Rutas", ubs.toArray());*/
                    return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
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
                if(furgonRepository.exists(furgonId)){
                    Furgon furgon = furgonRepository.findOne(furgonId);
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
                if(furgonRepository.exists(furgonId)){
                    Furgon furgon = furgonRepository.findOne(furgonId);
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
