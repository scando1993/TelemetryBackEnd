package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
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
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import net.pacificsoft.microservices.favorita.repository.TelemetriaRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class TelemetriaController {
	
	@Autowired
	private TelemetriaRepository telemetriaRepository;
        
        @Autowired
	private DeviceRepository deviceRepository;
	
	@GetMapping("/telemetria")
	public ResponseEntity getAllTelemetrias() {
		try{
                    return new ResponseEntity(telemetriaRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                } 
	}

	@GetMapping("/telemetria/{id}")
	public ResponseEntity getTelemetriaById(
			@PathVariable(value = "id") Long telemetriaId){
		if(telemetriaRepository.existsById(telemetriaId)){
                    Telemetria t = telemetriaRepository.findById(telemetriaId).get();
                    return new ResponseEntity(t, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Telemetria #" + telemetriaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/telemetria/{deviceid}")
	public ResponseEntity createTelemetria(@PathVariable(value = "deviceid") Long deviceid,
                               @Valid @RequestBody Telemetria telemetria) {
                if(deviceRepository.existsById(deviceid)){
                        Device device = deviceRepository.findById(deviceid).get();
                        device.getTelemetrias().add(telemetria);                 
                        telemetria.setDevice(device);
                        Telemetria t = telemetriaRepository.save(telemetria);
                        deviceRepository.save(device);
                        return new ResponseEntity(t, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("It's not possible create new Telemetria",
                                                        HttpStatus.NOT_FOUND);
                }
        }

	@PutMapping("/telemetria/{id}")
	public ResponseEntity updateTelemetria(
			@PathVariable(value = "id") Long telemetriaId,
			@Valid @RequestBody Telemetria telemetriaDetails){
                if(telemetriaRepository.existsById(telemetriaId)){
                    Telemetria telemetria = telemetriaRepository.findById(telemetriaId).get();
                    telemetria.setName(telemetriaDetails.getName());
                    telemetria.setDtm(telemetriaDetails.getDtm());
                    telemetria.setValue(telemetriaDetails.getValue());
                    final Telemetria updatedTelemetria = telemetriaRepository.save(telemetria);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Telemetria #" + telemetriaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/telemetria/{id}")
	public ResponseEntity deleteTelemetria(
			@PathVariable(value = "id") Long telemetriaId){
                if(telemetriaRepository.existsById(telemetriaId)){
                    Telemetria telemetria = telemetriaRepository.findById(telemetriaId).get();
                    telemetria.getDevice().getTelemetrias().remove(telemetria);
                    deviceRepository.save(telemetria.getDevice());
                    telemetriaRepository.delete(telemetria);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Telemetria #" + telemetriaId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
        
}
