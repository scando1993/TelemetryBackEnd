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
import net.pacificsoft.microservices.favorita.models.application.Bodega;
import net.pacificsoft.microservices.favorita.repository.application.BodegaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.application.Ciudad;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import net.pacificsoft.microservices.favorita.repository.application.CiudadRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import net.pacificsoft.microservices.favorita.repository.WifiScanRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class WifiScanController {
    
	@Autowired
	private WifiScanRepository wifiScanRepository;
        
        @Autowired
	private RawSensorDataRepository rawSensorDataRepository;
	
	@GetMapping("/wifiScan")
	public ResponseEntity getAllBodegas() {
                try{
                    return new ResponseEntity(wifiScanRepository.findAll(), HttpStatus.OK);
                }
		catch(Exception e){
                    return new ResponseEntity<String>("Resources not available.",
                            HttpStatus.NOT_FOUND);
                }  
	}

	@GetMapping("/wifiScan/{id}")
	public ResponseEntity getBodegaById(
			@PathVariable(value = "id") Long wifiId){
                if(wifiScanRepository.existsById(wifiId)){
                    WifiScan w = wifiScanRepository.findById(wifiId).get();
                    return new ResponseEntity(w, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("WifiScan #" + wifiId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/wifiScan/{rawdataId}")
	public ResponseEntity createBodega(@PathVariable(value = "rawdataId") Long rawId,
                                 @Valid @RequestBody WifiScan wifi) {
            try{
                if(rawSensorDataRepository.existsById(rawId)){
                    RawSensorData rw = rawSensorDataRepository.findById(rawId).get();
                    rw.getWifiScans().add(wifi);
                    wifi.setRawSensorData(rw);
                    WifiScan w = wifiScanRepository.save(wifi);
                    rawSensorDataRepository.save(rw);
                    return new ResponseEntity(w, HttpStatus.CREATED);
                }
                else{
                    return new ResponseEntity<String>("RawSensorData #" + rawId + 
                            " does not exist, it's not possible create new WifiScan", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new WifiScan", HttpStatus.NOT_FOUND);
                
            }
	}

        @PutMapping("/wifiScan/{id}")
	public ResponseEntity updateBodega(
			@PathVariable(value = "id") Long wifiId,
			@Valid @RequestBody WifiScan wifiDetails){
            try{
                if(wifiScanRepository.existsById(wifiId)) {
                    WifiScan wifi = wifiScanRepository.findById(wifiId).get();
                    wifi.setMAC(wifiDetails.getMAC());
                    wifi.setRSSI(wifiDetails.getRSSI());
                    final WifiScan wifiUpdate = wifiScanRepository.save(wifi);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("WifiScan #" + wifiId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update "
                        + "WifiScan #" + wifiId, HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/wifiScan/{id}")
	public ResponseEntity deleteBodega(
                                @PathVariable(value = "id") Long wifiId){
                if(wifiScanRepository.existsById(wifiId)){
                    WifiScan wifi = wifiScanRepository.findById(wifiId).get();
                    wifi.getRawSensorData().getWifiScans().remove(wifi);
                    rawSensorDataRepository.save(wifi.getRawSensorData());
                    wifiScanRepository.delete(wifi);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("WIfiScan #" + wifiId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
