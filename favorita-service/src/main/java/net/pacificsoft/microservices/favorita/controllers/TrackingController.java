package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.LocationGroup;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.LocationGroupRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class TrackingController {

    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private LocationGroupRepository locationGroupRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @GetMapping("/tracking")
    public ResponseEntity getAllTrackings() {
        try{
            return new ResponseEntity(trackingRepository.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tracking/{id}")
    public ResponseEntity getTrackingById(
            @PathVariable(value = "id") Long trackingId){
        if(trackingRepository.exists(trackingId)){
            Tracking tracking = trackingRepository.findOne(trackingId);
            return new ResponseEntity(tracking, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Tracking #" + trackingId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tracking/{deviceid}/{locationid}")
    public ResponseEntity createTracking(@Valid @RequestBody Tracking tracking,
                                         @PathVariable(value = "deviceid") Long deviceId,
                                         @PathVariable(value = "locationid") Long locationId) {
        try{
            if(deviceRepository.exists(deviceId) &&
                    locationGroupRepository.exists(locationId)){
                Device device = deviceRepository.findOne(deviceId);
                LocationGroup location = locationGroupRepository.findOne(locationId);
                device.getTrackings().add(tracking);
                location.getTrackings().add(tracking);
                tracking.setDevice(device);
                tracking.setLocationGroup(location);
                Tracking t = trackingRepository.save(tracking);
                deviceRepository.save(device);
                locationGroupRepository.save(location);
                return new ResponseEntity(t, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<String>("New Tracking not created.",
                        HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("New Tracking not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/tracking/{id}/{deviceid}/{locationid}")
    public ResponseEntity updateTracking(
            @PathVariable(value = "id") Long trackingId,
            @PathVariable(value = "deviceid") Long deviceId,
            @PathVariable(value = "locationid") Long locationId,
            @Valid @RequestBody Tracking trackingDetails){
        if(trackingRepository.exists(trackingId) &&
                deviceRepository.exists(deviceId) &&
                locationGroupRepository.exists(locationId)){
            Tracking tracking = trackingRepository.findOne(trackingId);
            Device device = deviceRepository.findOne(deviceId);
            LocationGroup location = locationGroupRepository.findOne(locationId);
            device.getTrackings().add(tracking);
            location.getTrackings().add(tracking);
            tracking.setLocation(trackingDetails.getLocation());
            tracking.setDtm(trackingDetails.getDtm());
            tracking.setDevice(device);
            tracking.setLocationGroup(location);
            final Tracking updatedTracking = trackingRepository.save(tracking);
            deviceRepository.save(device);
            locationGroupRepository.save(location);
            return new ResponseEntity(HttpStatus.OK);
        }

        else{
            return new ResponseEntity<String>("Tracking #" + trackingId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tracking/{id}")
    public ResponseEntity deleteTracking(
            @PathVariable(value = "id") Long trackingId){
        if(trackingRepository.exists(trackingId)){
            Tracking tracking = trackingRepository.findOne(trackingId);
            tracking.getDevice().getTrackings().remove(tracking);
            tracking.getLocationGroup().getTrackings().remove(tracking);
            deviceRepository.save(tracking.getDevice());
            locationGroupRepository.save(tracking.getLocationGroup());
            trackingRepository.delete(tracking);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Tracking #" + trackingId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
