package net.pacificsoft.microservices.favorita.controllers.application;

import java.text.SimpleDateFormat;
import net.pacificsoft.microservices.favorita.models.Device;
import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.application.Locales;
import net.pacificsoft.microservices.favorita.repository.application.FurgonRepository;
import net.pacificsoft.microservices.favorita.repository.application.LocalesRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
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
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.models.application.Producto;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import net.pacificsoft.microservices.favorita.repository.TelemetriaRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import net.pacificsoft.microservices.favorita.repository.application.ProductoRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

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
        
        @Autowired
	private TrackingRepository trackingRepository;
        
        @Autowired
	private TelemetriaRepository telemetriaRepository;
	
	@GetMapping("/ruta")
	public ResponseEntity getAllRutas() {
		try{
                    
                    List<Ruta> rutas = rutaRepository.findAll();
                    return new ResponseEntity(rutas, HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/ruta/{id}")
	public ResponseEntity getRutaById(
			@PathVariable(value = "id") Long rutaId){
		if(rutaRepository.existsById(rutaId)){
                    Ruta r = rutaRepository.findById(rutaId).get();
                    
                    return new ResponseEntity(r, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Ruta #" + rutaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/rutas")
	public ResponseEntity createRuta(@RequestParam Long furgon,
                                    @RequestParam Long device,
                                    @RequestParam Long producto,
                                    @RequestParam Long localInicio,
                                    @RequestParam Long localFin,
                               @Valid @RequestBody Ruta ruta) {
                if(furgonRepository.existsById(furgon) &&
                   deviceRepository.existsById(device) &&
                   localesRepository.existsById(localInicio) &&
                   localesRepository.existsById(localFin) &&
                   productoRepository.existsById(producto) ){
                    Furgon f = furgonRepository.findById(furgon).get();
                    f.getRutas().add(ruta);
                    Device d = deviceRepository.findById(device).get();
                    d.getRutas().add(ruta);
                    Locales lInicio = localesRepository.findById(localInicio).get();
                    lInicio.getRutasInicio().add(ruta);
                    Locales lFin = localesRepository.findById(localFin).get();
                    lFin.getRutasFin().add(ruta);
                    Producto p = productoRepository.findById(producto).get();
                    p.getRutas().add(ruta);
                    
                    Alerta a = new Alerta("ruta_registrada", "Se ha registrado la ruta");
                    a.setRuta(ruta);
                    a.setDevice(ruta.getDevice());
                    ruta.getDevice().getAlertas().add(a);
                    ruta.getAlertas().add(a);                    
                    ruta.setFurgon(f);
                    ruta.setProducto(p);
                    ruta.setDevice(d);
                    ruta.setLocalInicio(lInicio);
                    ruta.setLocalFin(lFin);
                    Ruta r = rutaRepository.save(ruta);
                    furgonRepository.save(f);
                    deviceRepository.save(d);
                    localesRepository.save(lInicio);
                    localesRepository.save(lFin);
                    productoRepository.save(p);
                    alertaRepository.save(a);
                    
                    return new ResponseEntity(r, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("It's not possible create new Ruta",
                                                        HttpStatus.NOT_FOUND);
                }
	}

	@PutMapping("/rutas/{id}")
	public ResponseEntity updateRuta(
			@PathVariable(value = "id") Long rutaId,
                        @RequestParam Long furgon,
                        @RequestParam Long device,
                        @RequestParam Long producto,
                        @RequestParam Long localInicio,
                        @RequestParam Long localFin,
			@Valid @RequestBody Ruta rutaDetails){
                if(rutaRepository.existsById(rutaId) &&
                   furgonRepository.existsById(furgon) &&
                   deviceRepository.existsById(device) &&
                   localesRepository.existsById(localInicio) &&
                   localesRepository.existsById(localFin) &&
                   productoRepository.existsById(producto)){
                    Ruta ruta = rutaRepository.findById(rutaId).get();
                    ruta.setStart_date(rutaDetails.getStart_date());
                    ruta.setEnd_date(rutaDetails.getEnd_date());
                    Furgon f = furgonRepository.findById(furgon).get();
                    f.getRutas().add(ruta);
                    Device d = deviceRepository.findById(device).get();
                    d.getRutas().add(ruta);
                    Locales lInicio = localesRepository.findById(localInicio).get();
                    lInicio.getRutasInicio().add(ruta);
                    Locales lFin = localesRepository.findById(localFin).get();
                    lFin.getRutasFin().add(ruta);
                    Producto p = productoRepository.findById(producto).get();
                    p.getRutas().add(ruta);
                    ruta.setFurgon(f);
                    ruta.setProducto(p);
                    ruta.setDevice(d);
                    ruta.setLocalInicio(lInicio);
                    ruta.setLocalFin(lFin);                    
                    Ruta r = rutaRepository.save(ruta);
                    furgonRepository.save(f);
                    deviceRepository.save(d);
                    localesRepository.save(lInicio);
                    localesRepository.save(lFin);
                    productoRepository.save(p);
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
                if(rutaRepository.existsById(rutaId)){
                    Ruta ruta = rutaRepository.findById(rutaId).get();
                    for(Alerta a:ruta.getAlertas()){
                        a.setRuta(null);
                        alertaRepository.save(a);
                    }
                    //ruta.getLocalFin().setRutaFin(null);
                    //ruta.getLocalInicio().setRutaInicio(null);
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
        
        @GetMapping("/getRutaTelemetrias")
	public ResponseEntity getDevicesTelemetrias(@RequestParam Long rutaid) {
            if(rutaRepository.existsById(rutaid)){
                Ruta ruta = rutaRepository.findById(rutaid).get();
                List<Tracking> trackings = trackingRepository.findByDtmBetweenAndDevice(ruta.getStart_date(), ruta.getEnd_date(),ruta.getDevice());
                JSONObject device;
                List<Map<String, Object>> result = new ArrayList();
                List<Map<String, Object>> telemetrias;
                JSONObject tel;
                for(Tracking t: trackings){
                     telemetrias = new ArrayList();
                     device = new JSONObject();
                     device.put("id", t.getDevice().getId());
                     device.put("name", t.getDevice().getName());
                     for(Telemetria telem: t.getDevice().getTelemetrias()){
                         tel = new JSONObject();
                         tel.put("dtm", telem.getDtm());
                         tel.put("value", telem.getValue());
                         telemetrias.add(tel.toMap());
                     }
                     device.put("telemetrias", telemetrias);
                     result.add(device.toMap());
                }
                return new ResponseEntity(result, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
            }
	}
        
        @GetMapping("/getTrackingsTelemetria")
	public ResponseEntity getTrackingTelemetry(@RequestParam Long telemetriaid) {
            if(telemetriaRepository.existsById(telemetriaid)){
                Telemetria telemetria = telemetriaRepository.findById(telemetriaid).get();
                Device device = telemetria.getDevice();
                JSONObject jdevice = new JSONObject();
                jdevice.put("id_device", device.getId());
                jdevice.put("name", device.getName());
                JSONObject jtracking;
                List<Map<String, Object>> result = new ArrayList();
                List<Map<String, Object>> trackings = new ArrayList();
                for(Tracking t: device.getTrackings()){
                    jtracking = new JSONObject();
                    jtracking.put("dtm", t.getDtm());
                    jtracking.put("location", t.getLocation());
                    trackings.add(jtracking.toMap());
                }
                jdevice.put("trackings", trackings);
{               return new ResponseEntity(jdevice.toMap(), HttpStatus.OK);
            }
            }
            else{
                return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
            }
        }
        
        @GetMapping("/getRutas")
	public ResponseEntity getRutas(){
            try{
		List<Ruta> rutas = rutaRepository.findByStatusNotLike("Finalizado");
                List<Map<String, Object>> result = new ArrayList();
                List<Map<String, Object>> telemetrias;
                JSONObject jProducto;
                JSONObject jDevice;
                JSONObject jRuta;
                JSONObject jTelemetria;
                List<Telemetria> ts;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                SimpleDateFormat formateT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                for(Ruta r: rutas){
                    telemetrias = new ArrayList();
                    Device d = r.getDevice();
                    jRuta = new JSONObject();
                    jDevice = new JSONObject();
                    jProducto = new JSONObject();
                    jRuta.put("id", r.getId());
                    jRuta.put("status", r.getStatus());
                    jRuta.put("start_date", simpleDateFormat.format(r.getStart_date()));
                    jRuta.put("end_date", simpleDateFormat.format(r.getEnd_date()));
                    jProducto.put("id", r.getProducto().getId());
                    jProducto.put("name", r.getProducto().getName());
                    jProducto.put("temp_max", r.getProducto().getTemp_max());
                    jProducto.put("temp_min", r.getProducto().getTemp_min());
                    jProducto.put("temp_max_ideal", r.getProducto().getTemp_max_ideal());
                    jProducto.put("temp_min_ideal", r.getProducto().getTemp_min_ideal());
                    ts = telemetriaRepository.findByDtmBetweenAndDeviceOrderByDtm(r.getStart_date(), r.getEnd_date(), d);
                    for(Telemetria tl: ts){
                        jTelemetria = new JSONObject();
                        jTelemetria.put("id", tl.getId());
                        jTelemetria.put("dtm", formateT.format(tl.getDtm()));
                        jTelemetria.put("name", tl.getName());
                        jTelemetria.put("value", tl.getValue());
                        telemetrias.add(jTelemetria.toMap());
                    }
                    jDevice.put("id", d.getId());
                    jDevice.put("name", d.getName());
                    jDevice.put("uuid", d.getUuid());
                    jDevice.put("family", d.getFamily());
                    jDevice.put("telemetrias", telemetrias.toArray());
                    jRuta.put("producto", jProducto);
                    jRuta.put("device", jDevice);
                    result.add(jRuta.toMap());
                }
                return new ResponseEntity(result, HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
         }
	}
        
        @GetMapping("/getAllRutas")
	public ResponseEntity getAll(){
            try{
		List<Ruta> rutas = rutaRepository.findAll();
                List<Map<String, Object>> result = new ArrayList();
                JSONObject jRuta;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                for(Ruta r: rutas){
                    Device d = r.getDevice();
                    jRuta = new JSONObject();
                    if(r.getProducto() != null){
                        jRuta.put("idProducto", r.getProducto().getId());
                        jRuta.put("nameProducto", r.getProducto().getName());
                        jRuta.put("temp_max", r.getProducto().getTemp_max());
                        jRuta.put("temp_min", r.getProducto().getTemp_min());
                        jRuta.put("temp_max_ideal", r.getProducto().getTemp_max_ideal());
                        jRuta.put("temp_min_ideal", r.getProducto().getTemp_min_ideal());
                    }
                    else{
                        jRuta.put("idProducto", "");
                        jRuta.put("nameProducto", "");
                        jRuta.put("temp_max", "");
                        jRuta.put("temp_min", "");
                        jRuta.put("temp_max_ideal", "");
                        jRuta.put("temp_min_ideal", "");
                    }
                    
                    if(r.getDevice() != null){
                        jRuta.put("idDevice", d.getId());
                        jRuta.put("nameDevice", d.getName());
                        jRuta.put("uuid", d.getUuid());
                        jRuta.put("familyDevice", d.getFamily());
                    }
                    else{
                        jRuta.put("idDevice", "");
                        jRuta.put("nameDevice", "");
                        jRuta.put("uuid", "");
                        jRuta.put("familyDevice", "");
                    }
                    
                    if(r.getLocalInicio() != null){
                        jRuta.put("idLocalInicio", r.getLocalInicio().getId());
                        jRuta.put("latitudeLocalInicio", r.getLocalInicio().getLatitude());
                        jRuta.put("lengthLocalInicio", r.getLocalInicio().getLength());
                        jRuta.put("familyLocalInicio", r.getLocalInicio().getFamily());
                        jRuta.put("nameLocalInicio", r.getLocalInicio().getName());
                        jRuta.put("numLocLocalInicio", r.getLocalInicio().getNumLoc());
                    }
                    else{
                        jRuta.put("idLocalInicio", "");
                        jRuta.put("latitudeLocalInicio", "");
                        jRuta.put("lengthLocalInicio", "");
                        jRuta.put("familyLocalInicio", "");
                        jRuta.put("nameLocalInicio", "");
                        jRuta.put("numLocLocalInicio", "");
                    }
                    
                    if(r.getLocalFin() != null){
                        jRuta.put("idLocalFin", r.getLocalFin().getId());
                        jRuta.put("latitudeLocalFin", r.getLocalFin().getLatitude());
                        jRuta.put("lengthLocalFin", r.getLocalFin().getLength());
                        jRuta.put("familyLocalFin", r.getLocalFin().getFamily());
                        jRuta.put("nameLocalFin", r.getLocalFin().getName());
                        jRuta.put("numLocLocalFin", r.getLocalFin().getNumLoc());
                    }
                    else{
                        jRuta.put("idLocalFin", "");
                        jRuta.put("latitudeLocalFin", "");
                        jRuta.put("lengthLocalFin", "");
                        jRuta.put("familyLocalFin", "");
                        jRuta.put("nameLocalFin", "");
                        jRuta.put("numLocLocalFin", "");
                    }
                    
                    if(r.getFurgon() != null){
                        jRuta.put("idFurgon", r.getFurgon().getId());
                        jRuta.put("numFurgon", r.getFurgon().getNumFurgon());
                        jRuta.put("nameFurgon", r.getFurgon().getName());
                    }
                    else{
                        jRuta.put("idFurgon", "");
                        jRuta.put("numFurgon", "");
                        jRuta.put("nameFurgon", "");
                    }
                    jRuta.put("idRuta", r.getId());
                    jRuta.put("status", r.getStatus());
                    jRuta.put("start_date", simpleDateFormat.format(r.getStart_date()));
                    jRuta.put("end_date", simpleDateFormat.format(r.getEnd_date()));
                    result.add(jRuta.toMap());
                }
                return new ResponseEntity(result, HttpStatus.OK);
            }
            catch (Exception e){
                return new ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR);
         }
	}
}
