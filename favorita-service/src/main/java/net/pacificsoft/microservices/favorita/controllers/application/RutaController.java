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

	@PostMapping("/ruta/{furgonid}/{deviceid}/{productid}/{localinicioid}/{localfinid}")
	public ResponseEntity createRuta(@PathVariable(value = "furgonid") Long furgonid,
                               @PathVariable(value = "deviceid") Long deviceid,
                               @PathVariable(value = "productid") Long productid,
                               @PathVariable(value = "localinicioid") Long localinicioid,
                               @PathVariable(value = "localfinid") Long localfinid,
                               @Valid @RequestBody Ruta ruta) {
                if(furgonRepository.existsById(furgonid) &&
                   deviceRepository.existsById(deviceid) &&
                   localesRepository.existsById(localinicioid) &&
                   localesRepository.existsById(localfinid) &&
                   productoRepository.existsById(productid) ){
                    Furgon furgon = furgonRepository.findById(furgonid).get();
                    furgon.getRutas().add(ruta);
                    Device device = deviceRepository.findById(deviceid).get();
                    device.getRutas().add(ruta);
                    Locales localInicio = localesRepository.findById(localinicioid).get();
                    //localInicio.setRutaInicio(ruta);
                    Locales localFin = localesRepository.findById(localfinid).get();
                    //localFin.setRutaFin(ruta);
                    Producto producto = productoRepository.findById(productid).get();
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
                    
                    return new ResponseEntity(r, HttpStatus.CREATED);
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
                if(rutaRepository.existsById(rutaId) &&
                   furgonRepository.existsById(furgonid) &&
                   deviceRepository.existsById(deviceid) &&
                   localesRepository.existsById(localinicioid) &&
                   localesRepository.existsById(localfinid) &&
                   productoRepository.existsById(productid)){
                    Ruta ruta = rutaRepository.findById(rutaId).get();
                    ruta.setStart_date(rutaDetails.getStart_date());
                    ruta.setEnd_date(rutaDetails.getEnd_date());
                    Furgon furgon = furgonRepository.findById(furgonid).get();
                    furgon.getRutas().add(ruta);
                    Device device = deviceRepository.findById(deviceid).get();
                    device.getRutas().add(ruta);
                    Locales localInicio = localesRepository.findById(localinicioid).get();
                    //localInicio.setRutaInicio(ruta);
                    Locales localFin = localesRepository.findById(localfinid).get();
                    //localFin.setRutaFin(ruta);
                    Producto producto = productoRepository.findById(productid).get();
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
                JSONObject jProducto;
                JSONObject jDevice;
                JSONObject jRuta;
                List<Telemetria> ts;
                for(Ruta r: rutas){
                    Device d = r.getDevice();
                    jRuta = new JSONObject();
                    jDevice = new JSONObject();
                    jProducto = new JSONObject();
                    jRuta.put("id", r.getId());
                    jRuta.put("status", r.getStatus());
                    jRuta.put("start_date", r.getStart_date());
                    jRuta.put("end_date", r.getEnd_date());
                    jProducto.put("id", r.getProducto().getId());
                    jProducto.put("name", r.getProducto().getName());
                    jProducto.put("temp_max", r.getProducto().getTemp_max());
                    jProducto.put("temp_min", r.getProducto().getTemp_min());
                    jProducto.put("temp_max_ideal", r.getProducto().getTemp_max_ideal());
                    jProducto.put("temp_min_ideal", r.getProducto().getTemp_min_ideal());
                    ts = telemetriaRepository.findByDtmBetweenAndDeviceOrderByDtm(r.getStart_date(), r.getEnd_date(), d);
                    jDevice.put("id", d.getId());
                    jDevice.put("name", d.getName());
                    jDevice.put("uuid", d.getUuid());
                    jDevice.put("family", d.getFamily());
                    jDevice.put("telemetrias", ts.toArray());
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
}
