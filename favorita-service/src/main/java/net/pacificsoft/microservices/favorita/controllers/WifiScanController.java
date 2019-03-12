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
import net.pacificsoft.springbootcrudrest.model.Bodega;
import net.pacificsoft.springbootcrudrest.repository.BodegaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.springbootcrudrest.model.Ciudad;
import net.pacificsoft.springbootcrudrest.model.RawSensorData;
import net.pacificsoft.springbootcrudrest.model.WifiScan;
import net.pacificsoft.springbootcrudrest.repository.CiudadRepository;
import net.pacificsoft.springbootcrudrest.repository.RawSensorDataRepository;
import net.pacificsoft.springbootcrudrest.repository.WifiScanRepository;
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
                if(wifiScanRepository.exists(wifiId)){
                    WifiScan w = wifiScanRepository.findOne(wifiId);
                    return new ResponseEntity(w, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("WifiScan #" + wifiId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/wifiScan/{rawdataId}")
	public ResponseEntity createBodega(@PathVariable(value = "ciudadid") Long rawId,
                                 @Valid @RequestBody WifiScan wifi) {
            try{
                if(rawSensorDataRepository.exists(rawId)){
                    RawSensorData rw = rawSensorDataRepository.findOne(rawId);
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
                if(wifiScanRepository.exists(wifiId)) {
                    WifiScan wifi = wifiScanRepository.findOne(wifiId);
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
                if(wifiScanRepository.exists(wifiId)){
                    WifiScan wifi = wifiScanRepository.findOne(wifiId);
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
