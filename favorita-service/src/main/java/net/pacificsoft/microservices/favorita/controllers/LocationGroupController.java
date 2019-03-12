package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.LocationGroup;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.LocationGroupRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class LocationGroupController {

    @Autowired
    private LocationGroupRepository locationGroupRepository;

    @Autowired
    private TrackingRepository trackingRepository;

    @GetMapping("/locationGroup")
    public ResponseEntity getAllLocationsGroups() {
        try{
            List<Map<String, Object>> result = new ArrayList();
            Set<Map<String, Object>> ubs;
            JSONObject json;
            JSONObject resp;
            List<LocationGroup> locations = locationGroupRepository.findAll();
            for (LocationGroup lg : locations){
                json = new JSONObject();
                ubs = new HashSet();
                json.put("id", lg.getId());
                json.put("name",lg.getName());
                Set<Tracking> trackings = lg.getTrackings();
                for (Tracking t: trackings){
                    resp = new JSONObject();
                    resp.put("id", t.getId());
                    resp.put("Tracking_Dtm", t.getDtm());
                    resp.put("Tracking_location", t.getLocation());
                    ubs.add(resp.toMap());
                }
                json.put("Trackings", ubs.toArray());
                result.add(json.toMap());
            }
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/locationGroup/{id}")
    public ResponseEntity getLocationGroupById(
            @PathVariable(value = "id") Long locationId){
        if(locationGroupRepository.exists(locationId)){
            LocationGroup lg = locationGroupRepository.findOne(locationId);
            JSONObject json = new JSONObject();
            JSONObject resp = new JSONObject();
            Set<Map<String, Object>> ubs = new HashSet();
            json.put("id", lg.getId());
            json.put("name",lg.getName());
            Set<Tracking> trackings = lg.getTrackings();
            for (Tracking t: trackings){
                resp = new JSONObject();
                resp.put("id", t.getId());
                resp.put("Tracking_Dtm", t.getDtm());
                resp.put("Tracking_location", t.getLocation());
                ubs.add(resp.toMap());
            }
            json.put("Trackings", ubs.toArray());
            return new ResponseEntity(json.toMap(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("LocationGroup #" + locationId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/locationGroup")
    public ResponseEntity createLocationGroup(@Valid @RequestBody LocationGroup location) {
        try{
            LocationGroup lg = locationGroupRepository.save(location);
            JSONObject json = new JSONObject();
            JSONObject resp = new JSONObject();
            Set<Map<String, Object>> ubs = new HashSet();
            json.put("id", lg.getId());
            json.put("name",lg.getName());
            Set<Tracking> trackings = lg.getTrackings();
            for (Tracking t: trackings){
                resp = new JSONObject();
                resp.put("id", t.getId());
                resp.put("Tracking_Dtm", t.getDtm());
                resp.put("Tracking_location", t.getLocation());
                ubs.add(resp.toMap());
            }
            json.put("Trackings", ubs.toArray());
            return new ResponseEntity(json.toMap(), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<String>("New LocationGroup not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/locationGroup/{id}")
    public ResponseEntity updateLocationGroup(
            @PathVariable(value = "id") Long locationId,
            @Valid @RequestBody LocationGroup locationDetails){
        try{
            if(locationGroupRepository.exists(locationId)){
                LocationGroup location = locationGroupRepository.findOne(locationId);
                location.setName(locationDetails.getName());
                final LocationGroup updatedLocation = locationGroupRepository.save(location);
                return new ResponseEntity(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("LocationGroup #" + locationId +
                        " does not exist.", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible update LocationGroup #" + locationId, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/locationGroup/{id}")
    public ResponseEntity deleteLocationGroup(
            @PathVariable(value = "id") Long locationId){
        if(locationGroupRepository.exists(locationId)){
            LocationGroup location = locationGroupRepository.findOne(locationId);
            if(location.getTrackings().size()>0){
                Set<Tracking> ubFs = location.getTrackings();
                for(Tracking t:ubFs){
                    t.setLocationGroup(null);
                    trackingRepository.save(t);
                }
            }
            locationGroupRepository.delete(location);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("LocationGroup#" + locationId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
