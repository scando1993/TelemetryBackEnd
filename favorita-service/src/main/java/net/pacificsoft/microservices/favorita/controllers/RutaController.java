package net.pacificsoft.springbootcrudrest.controller;

import java.text.SimpleDateFormat;
import net.pacificsoft.springbootcrudrest.model.Device;
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
import net.pacificsoft.springbootcrudrest.model.Ruta;
import net.pacificsoft.springbootcrudrest.repository.DeviceRepository;
import net.pacificsoft.springbootcrudrest.repository.FurgonRepository;
import net.pacificsoft.springbootcrudrest.repository.RutaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.springbootcrudrest.model.Alerta;
import net.pacificsoft.springbootcrudrest.model.Locales;
import net.pacificsoft.springbootcrudrest.model.Producto;
import net.pacificsoft.springbootcrudrest.repository.AlertaRepository;
import net.pacificsoft.springbootcrudrest.repository.LocalesRepository;
import net.pacificsoft.springbootcrudrest.repository.ProductoRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class RutaController {
	
	@Autowired
	private RutaRepository rutaRepository;
        
        @Autowired
	private FurgonRepository furgonRepository;
        
        @Autowired
	private DeviceRepository deviceRepository;
        
        @Autowired
	private LocalesRepository localesRepository;
        
        @Autowired
	private ProductoRepository productoRepository;
        
        @Autowired
	private AlertaRepository alertaRepository;
	
	@GetMapping("/ruta")
	public ResponseEntity getAllRutas() {
		try{
                    List<Map<String, Object>> result = new ArrayList();
                    List<Ruta> rutas = rutaRepository.findAll();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    JSONObject json;
                    for(Ruta r:rutas){
                        json = new JSONObject();
                        json.put("id", r.getId());
                        json.put("startDate",format.format(r.getStart_date()));
                        json.put("endDate",format.format(r.getEnd_date()));
                        json.put("nameProduct",r.getProducto().getName());
                        json.put("nameFurgon",r.getFurgon().getName());
                        json.put("nameDevice",r.getDevice().getName());
                        /*if(r.getLocalInicio().getCiudad().getProvincia().getZona() != null)
                            json.put("nameStartZone", r.getLocalInicio().getCiudad().
                                                getProvincia().getZona().getName());
                        if(r.getLocalFin().getCiudad().getProvincia().getZona() != null)
                            json.put("nameEndZone",r.getLocalFin().getCiudad().
                                                getProvincia().getZona().getName());  */                      
                        json.put("nameStartLocal",r.getLocalInicio().getName());
                        json.put("nameEndLocal",r.getLocalFin().getName());
                        result.add(json.toMap());
                    }
                    return new ResponseEntity(result, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/ruta/{id}")
	public ResponseEntity getRutaById(
			@PathVariable(value = "id") Long rutaId){
		if(rutaRepository.exists(rutaId)){
                    Ruta r = rutaRepository.findOne(rutaId);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    JSONObject json = new JSONObject();
                    json.put("id", r.getId());
                    json.put("startDate",format.format(r.getStart_date()));
                    json.put("endDate", format.format(r.getEnd_date()));
                    json.put("nameProduct",r.getProducto().getName());
                    json.put("nameFurgon",r.getFurgon().getName());
                    json.put("nameDevice",r.getDevice().getName());                        
                    /*if(r.getLocalInicio().getCiudad().getProvincia().getZona() != null)
                        json.put("nameStartZone",r.getLocalInicio().getCiudad().
                                            getProvincia().getZona().getName());
                    if(r.getLocalFin().getCiudad().getProvincia().getZona() != null)
                        json.put("nameEndZone",r.getLocalFin().getCiudad().
                                            getProvincia().getZona().getName()); */                       
                    json.put("nameStartLocal",r.getLocalInicio().getName());
                    json.put("nameEndLocal",r.getLocalFin().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Ruta #" + rutaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/ruta/{furgonid}/{deviceid}/{productid}/{localinicioid}/{localfinid}")
	public ResponseEntity createRuta(@PathVariable(value = "furgonid") Long furgonid,
                               @PathVariable(value = "deviceid") Long deviceid,
                               @PathVariable(value = "productid") Long productid,
                               @PathVariable(value = "localinicioid") Long localinicioid,
                               @PathVariable(value = "localfinid") Long localfinid,
                               @Valid @RequestBody Ruta ruta) {
                if(furgonRepository.exists(furgonid) &&
                   deviceRepository.exists(deviceid) &&
                   localesRepository.exists(localinicioid) &&
                   localesRepository.exists(localfinid) &&
                   productoRepository.exists(productid) ){
                    Furgon furgon = furgonRepository.findOne(furgonid);
                    furgon.getRutas().add(ruta);
                    Device device = deviceRepository.findOne(deviceid);
                    device.getRutas().add(ruta);
                    Locales localInicio = localesRepository.findOne(localinicioid);
                    localInicio.setRutaInicio(ruta);
                    Locales localFin = localesRepository.findOne(localfinid);
                    localFin.setRutaFin(ruta);
                    Producto producto = productoRepository.findOne(productid);
                    producto.getRutas().add(ruta);
                    ruta.setFurgon(furgon);
                    ruta.setProducto(producto);
                    ruta.setDevice(device);
                    ruta.setLocalInicio(localInicio);
                    ruta.setLocalFin(localFin);
                    Ruta r = rutaRepository.save(ruta);
                    furgonRepository.save(furgon);
                    deviceRepository.save(device);
                    localesRepository.save(localInicio);
                    localesRepository.save(localFin);
                    productoRepository.save(producto);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    JSONObject json = new JSONObject();
                    json.put("id", r.getId());
                    json.put("startDate",format.format(r.getStart_date()));
                    json.put("endDate", format.format(r.getEnd_date()));
                    json.put("nameProduct",r.getProducto().getName());
                    json.put("nameFurgon",r.getFurgon().getName());
                    json.put("nameDevice",r.getDevice().getName());
                    /*if(r.getLocalInicio().getCiudad().getProvincia().getZona() != null)
                        json.put("nameStartZone",r.getLocalInicio().getCiudad().
                                            getProvincia().getZona().getName());
                    if(r.getLocalFin().getCiudad().getProvincia().getZona() != null)
                        json.put("nameEndZone",r.getLocalFin().getCiudad().
                                            getProvincia().getZona().getName());*/
                    json.put("nameStartLocal",r.getLocalInicio().getName());
                    json.put("nameEndLocal",r.getLocalFin().getName());
                    return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("It's not possible create new Ruta",
                                                        HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/ruta/{id}/{furgonid}/{deviceid}/{productid}/{localinicioid}/{localfinid}")
	public ResponseEntity updateRuta(
			@PathVariable(value = "id") Long rutaId,
                        @PathVariable(value = "furgonid") Long furgonid,
                        @PathVariable(value = "deviceid") Long deviceid,
                        @PathVariable(value = "productid") Long productid,
                        @PathVariable(value = "localinicioid") Long localinicioid,
                        @PathVariable(value = "localfinid") Long localfinid,
			@Valid @RequestBody Ruta rutaDetails){
                if(rutaRepository.exists(rutaId) &&
                   furgonRepository.exists(furgonid) &&
                   deviceRepository.exists(deviceid) &&
                   localesRepository.exists(localinicioid) &&
                   localesRepository.exists(localfinid) &&
                   productoRepository.exists(productid)){
                    Ruta ruta = rutaRepository.findOne(rutaId);
                    ruta.setStart_date(rutaDetails.getStart_date());
                    ruta.setEnd_date(rutaDetails.getEnd_date());
                    Furgon furgon = furgonRepository.findOne(furgonid);
                    furgon.getRutas().add(ruta);
                    Device device = deviceRepository.findOne(deviceid);
                    device.getRutas().add(ruta);
                    Locales localInicio = localesRepository.findOne(localinicioid);
                    localInicio.setRutaInicio(ruta);
                    Locales localFin = localesRepository.findOne(localfinid);
                    localFin.setRutaFin(ruta);
                    Producto producto = productoRepository.findOne(productid);
                    producto.getRutas().add(ruta);
                    ruta.setFurgon(furgon);
                    ruta.setProducto(producto);
                    ruta.setDevice(device);
                    ruta.setLocalInicio(localInicio);
                    ruta.setLocalFin(localFin);                    
                    Ruta r = rutaRepository.save(ruta);
                    furgonRepository.save(furgon);
                    deviceRepository.save(device);
                    localesRepository.save(localInicio);
                    localesRepository.save(localFin);
                    productoRepository.save(producto);
                    final Ruta updatedRuta = rutaRepository.save(ruta);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("It's not possible update "
                            + "ruta #"+rutaId, HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/ruta/{id}")
	public ResponseEntity deleteRuta(
			@PathVariable(value = "id") Long rutaId){
                if(rutaRepository.exists(rutaId)){
                    Ruta ruta = rutaRepository.findOne(rutaId);
                    for(Alerta a:ruta.getAlertas()){
                        a.setRuta(null);
                        alertaRepository.save(a);
                    }
                    ruta.getLocalFin().setRutaFin(null);
                    ruta.getLocalInicio().setRutaInicio(null);
                    ruta.getFurgon().getRutas().remove(ruta);
                    ruta.getProducto().getRutas().remove(ruta);
                    ruta.getDevice().getRutas().remove(ruta);
                    furgonRepository.save(ruta.getFurgon());
                    localesRepository.save(ruta.getLocalInicio());
                    localesRepository.save(ruta.getLocalFin());
                    deviceRepository.save(ruta.getDevice());
                    productoRepository.save(ruta.getProducto());
                    rutaRepository.delete(ruta);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Ruta #" + rutaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
        
}
