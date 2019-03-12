package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.models.Location;
import net.pacificsoft.microservices.favorita.repository.AliasLocationRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;
import net.pacificsoft.microservices.favorita.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class LocationController {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AliasLocationRepository aliasLocationRepository;


    @GetMapping("/location")
    public ResponseEntity getAllLocations() {
        try {
            return new ResponseEntity(locationRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/location/{id}")
    public ResponseEntity getLocationById(
            @PathVariable(value = "id") Long locationId) {
        if (locationRepository.existsById(locationId)) {
            Location location = locationRepository.findById(locationId).get();
            return new ResponseEntity(location, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Location #" + locationId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/location/{groupid}")
    public ResponseEntity createLocation(@PathVariable(value = "groupid") Long groupid,
                                         @Valid @RequestBody Location location) {
        if (groupRepository.existsById(groupid)) {
            Group group = groupRepository.findById(groupid).get();
            location.setGroup(group);
            group.getLocations().add(location);
            Location l = locationRepository.save(location);
            groupRepository.save(group);
            return new ResponseEntity(l, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<String>("Group #" + groupid +
                    " does not exist, isn't possible create new Location", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/location/{id}")
    public ResponseEntity updateLocation(
            @PathVariable(value = "id") Long locationId,
            @Valid @RequestBody Location locationDetails) {
        if (locationRepository.existsById(locationId)) {
            Location location = locationRepository.findById(locationId).get();
            location.setName(locationDetails.getName());
            final Location updatedLocation = locationRepository.save(location);
            return new ResponseEntity(updatedLocation, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Location #" + locationId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/location/{id}")
    public ResponseEntity deleteLocation(
            @PathVariable(value = "id") Long locationId) {
        if (locationRepository.existsById(locationId)) {
            Location location = locationRepository.findById(locationId).get();
            location.getGroup().getLocations().remove(location);
            if (location.getAliasLocation() != null) {
                location.getAliasLocation().setLocation(null);
                aliasLocationRepository.save(location.getAliasLocation());
            }
            groupRepository.save(location.getGroup());
            locationRepository.delete(location);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Location #" + locationId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
