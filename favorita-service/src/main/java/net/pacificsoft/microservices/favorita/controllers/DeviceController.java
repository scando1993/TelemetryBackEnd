package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private TrackingRepository trackingRepository;

    @GetMapping("/device")
    public ResponseEntity getAllDevices() {
        try {
            return new ResponseEntity(deviceRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/device/{id}")
    public ResponseEntity getDeviceById(
            @PathVariable(value = "id") Long deviceId) {
        if (deviceRepository.existsById(deviceId)) {
            Device device = deviceRepository.findById(deviceId).get();
            return new ResponseEntity(device, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Device #" + deviceId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/device/{trackingid}")
    public ResponseEntity createDevice(@PathVariable(value = "trackingid") Long trackingid,
                                       @Valid @RequestBody Device device) {
        if (trackingRepository.existsById(trackingid)) {
            Tracking tracking = trackingRepository.findById(trackingid).get();
            tracking.getDevices().add(device);
            device.setTracking(tracking);
            Device d = deviceRepository.save(device);
            trackingRepository.save(tracking);
            return new ResponseEntity(d, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Tracking #" + trackingid +
                    " does not exist, isn't possible create new Device", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/device/{id}")
    public ResponseEntity updateDevice(
            @PathVariable(value = "id") Long deviceId,
            @Valid @RequestBody Device deviceDetails) {
        if (deviceRepository.existsById(deviceId)) {
            Device device = deviceRepository.findById(deviceId).get();
            device.setFamily(deviceDetails.getFamily());
            device.setName(deviceDetails.getName());
            final Device updatedDevice = deviceRepository.save(device);
            return new ResponseEntity(updatedDevice, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Device #" + deviceId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/device/{id}")
    public ResponseEntity deleteDevice(
            @PathVariable(value = "id") Long deviceId) {
        if (deviceRepository.existsById(deviceId)) {
            Device device = deviceRepository.findById(deviceId).get();
            device.getTracking().getDevices().remove(device);
            trackingRepository.save(device.getTracking());
            deviceRepository.delete(device);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Device #" + deviceId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
