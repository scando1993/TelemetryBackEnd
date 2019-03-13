package net.pacificsoft.microservices.favorita.controllers;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.Family;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.repository.FamilyRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.repository.BodegaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.pacificsoft.microservices.favorita.models.Ciudad;
import net.pacificsoft.microservices.favorita.repository.CiudadRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FamilyController {
    @Autowired
    private FamilyRepository familyRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping("/family")
    public ResponseEntity getAllBodegas() {
        try{
            List<Family> families = familyRepository.findAll();

            return new ResponseEntity(families, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/family/{groupID}")
    public ResponseEntity createCiudad(@PathVariable(value = "groupID") Long groupID,
                                       @Valid @RequestBody Family family) {
        try{
            if(groupRepository.existsById(groupID)){
                Group group = groupRepository.findById(groupID).get();

                family.setGroup(group);
                group.getFamilies().add(family);;

                groupRepository.save(group);
                Family posted = familyRepository.save(family);

                return new ResponseEntity(posted, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<String>("GroupFamily #" + groupID +
                        " does not exist, it's not possible create new Prediction", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Family", HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/family/{id}")
    public ResponseEntity updateCiudad(
            @PathVariable(value = "id") Long familyID,
            @Valid @RequestBody Family familyDetails){
        try{
            if(familyRepository.existsById(familyID)) {
                Family family = familyRepository.findById(familyID).get();
                family.setName(familyDetails.getName());
                final Family updated = familyRepository.save(family);
                return new ResponseEntity(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("Family #" + familyID +
                        " does not exist.", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible update "
                    + "Family #" + familyID, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/family/{id}")
    public ResponseEntity deleteCiudad(
            @PathVariable(value = "id") Long familyID){
        if(familyRepository.existsById(familyID)){
            Family family = familyRepository.findById(familyID).get();
            familyRepository.delete(family);
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Family #" + familyID +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }

    }
}
