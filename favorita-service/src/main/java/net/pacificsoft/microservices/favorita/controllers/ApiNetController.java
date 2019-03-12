package net.pacificsoft.springbootcrudrest.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.springbootcrudrest.model.Device;
import net.pacificsoft.springbootcrudrest.model.Tracking;
import net.pacificsoft.springbootcrudrest.repository.DeviceRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/tracking")
public class ApiNetController {
    
        @Autowired
	private DeviceRepository deviceRepository;
        
        @GetMapping("/getAllDevice")
	public ResponseEntity getAllDevices(@RequestParam String family) {
            List<Map<String,Object>> result = new ArrayList();
            List<Device> devices = deviceRepository.findAll();
            JSONObject json;
            for(Device d: devices){
                if(d.getFamily().equals(family)){
                    json = new JSONObject();
                    json.put("IdDevice", d.getId());
                    json.put("Family", d.getFamily());
                    json.put("Name", d.getName());
                    result.add(json.toMap());
                }
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }

        @GetMapping("/getAllFamilies")
	public ResponseEntity getAllFamilies() {
            List<Map<String,Object>> result = new ArrayList();
            List<Device> devices = deviceRepository.findAll();
            List<String> families = new ArrayList();
            JSONObject json;
            for(Device d: devices){
                if(!families.contains(d.getFamily())){
                    families.add(d.getFamily());
                    json = new JSONObject();
                    json.put("family", d.getFamily());
                    result.add(json.toMap());
                }
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        
        @GetMapping("/trackAll")
	public ResponseEntity getAllTrackings(@RequestParam String name,
                            @RequestParam String family) {
            List<Map<String,Object>> result = new ArrayList();
            List<Device> devices = deviceRepository.findAll();
            JSONObject json;
            JSONObject jDevice;
            for(Device d: devices){
                if(d.getName().equals(name) &&
                   d.getFamily().equals(family)){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    jDevice = new JSONObject();
                    jDevice.put("IdDevice", d.getId());
                    jDevice.put("Family", d.getFamily());
                    jDevice.put("Name", d.getName());
                    for(Tracking t: d.getTrackings()){
                        json = new JSONObject();
                        json.put("Id", t.getId());
                        json.put("Dtm", format.format(t.getDtm()));
                        json.put("DeviceId", d.getId());
                        json.put("Location", t.getLocation());
                        json.put("Device", jDevice.toMap());
                        result.add(json.toMap());
                    }
                    
                }
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        
        /*@GetMapping("/groupby")
	public ResponseEntity getTrackingsFamily(
                @RequestParam String family) {
            List<Map<String,Object>> result = new ArrayList();
            List<Map<String,Object>> trackings;
            List<Device> devices = deviceRepository.findAll();
            JSONObject json;
            JSONObject jTracking;
            JSONObject jDevices;
            for(Device d: devices){
                if(d.getFamily().equals(family)){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    trackings = new ArrayList();
                    json = new JSONObject();
                    jDevices = new JSONObject();
                    Tracking last = new Tracking();
                    jDevices.put("DeviceName", d.getName());
                    for(Tracking t: d.getTrackings()){
                        jTracking = new JSONObject();
                        jTracking.put("Location", t.getLocation());
                        jTracking.put("Dtm", format.format(t.getDtm()));
                        trackings.add(jTracking.toMap());
                        last = t;
                    }
                    jDevices.put("Trackings", trackings);
                    json.put("LocationName", last.getLocation());
                    json.put("Devices", trackings);
                    result.add(json.toMap());
                }
            }
            return null;
        }
        
        @GetMapping("/groupby2")
	public ResponseEntity getTrackingsFamilyLocation() {
            
            return null;
        }
        
        @GetMapping("/lastTemperature")
	public ResponseEntity getTrackingsNameFamily() {
            
            return null;
        }
        
        @GetMapping("/getDeviceInfo")
	public ResponseEntity getDeviceInfo() {
            
            return null;
        }
        
        @GetMapping("/report")
	public ResponseEntity getTrackingReport() {
            
            return null;
        }*/
}
