package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.models.Location;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;
import net.pacificsoft.microservices.favorita.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api")
public class GroupController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/group")
    public ResponseEntity getAllGroups() {
        try {
            return new ResponseEntity(groupRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/group/{id}")
    public ResponseEntity getGroupById(
            @PathVariable(value = "id") Long groupId) {
        if (groupRepository.existsById(groupId)) {
            Group group = groupRepository.findById(groupId).get();
            return new ResponseEntity(group, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Group #" + groupId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/group")
    public ResponseEntity createGroup(@Valid @RequestBody Group group) {
        try {
            Group g = groupRepository.save(group);
            return new ResponseEntity(g, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("New Group not created", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/group/{id}")
    public ResponseEntity updateGroup(
            @PathVariable(value = "id") Long groupId,
            @Valid @RequestBody Group groupDetails) {
        if (groupRepository.existsById(groupId)) {
            Group group = groupRepository.findById(groupId).get();
            group.setName(groupDetails.getName());
            final Group updatedGroup = groupRepository.save(group);
            return new ResponseEntity(updatedGroup, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Group #" + groupId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/group/{id}")
    public ResponseEntity deleteGroup(
            @PathVariable(value = "id") Long groupId) {
        if (groupRepository.existsById(groupId)) {
            Group group = groupRepository.findById(groupId).get();
            if (group.getLocations().size() > 0) {
                Set<Location> locations = group.getLocations();
                for (Location l : locations) {
                    l.setGroup(null);
                    locationRepository.save(l);
                }
            }
            groupRepository.delete(group);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Group #" + groupId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
