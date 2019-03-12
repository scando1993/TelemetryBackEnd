package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RutaRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class TrackingController {

    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @GetMapping("/tracking")
    public ResponseEntity getAllTrackings() {
        try {
            return new ResponseEntity(trackingRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/tracking/{id}")
    public ResponseEntity getTrackingById(
            @PathVariable(value = "id") Long trackingId) {
        if (trackingRepository.existsById(trackingId)) {
            Tracking tracking = trackingRepository.findById(trackingId).get();
            return new ResponseEntity(tracking, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Tracking #" + trackingId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tracking")
    public ResponseEntity createTracking(@Valid @RequestBody Tracking tracking) {
        try {
            Tracking t = trackingRepository.save(tracking);
            return new ResponseEntity(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("New Tracking not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/tracking/{id}")
    public ResponseEntity updateTracking(
            @PathVariable(value = "id") Long trackingId,
            @Valid @RequestBody Tracking trackingDetails) {
        if (trackingRepository.existsById(trackingId)) {
            Tracking tracking = trackingRepository.findById(trackingId).get();
            tracking.setTemperature(trackingDetails.getTemperature());
            tracking.setLocation(trackingDetails.getLocation());
            tracking.setDevices(trackingDetails.getDevices());
            final Tracking updatedTracking = trackingRepository.save(tracking);
            return new ResponseEntity(updatedTracking, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Tracking #" + trackingId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tracking/{id}")
    public ResponseEntity deleteTracking(
            @PathVariable(value = "id") Long trackingId) {
        if (trackingRepository.existsById(trackingId)) {
            Tracking tracking = trackingRepository.findById(trackingId).get();
            if (tracking.getDevices().size() > 0) {
                Set<Device> devices = tracking.getDevices();
                for (Device d : devices) {
                    d.setTracking(null);
                    deviceRepository.save(d);
                }
            }
            trackingRepository.delete(tracking);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Tracking #" + trackingId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
