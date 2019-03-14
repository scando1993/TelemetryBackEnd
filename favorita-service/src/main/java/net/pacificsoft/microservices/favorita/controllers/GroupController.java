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
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Family;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.models.application.Ciudad;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.FamilyRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;
import net.pacificsoft.microservices.favorita.repository.application.CiudadRepository;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/api")
public class GroupController {
    
	@Autowired
	private GroupRepository gRepository;
        
        @Autowired
	private FamilyRepository familyRepository;
        
        @Autowired
	private DeviceRepository deviceRepository;
        
	@GetMapping("/group")
	public ResponseEntity getAllBodegas() {
                return new ResponseEntity(gRepository.findAll(), HttpStatus.OK); 
	}
        
	@GetMapping("/group/{id}")
	public ResponseEntity getBodegaById(
			@PathVariable(value = "id") Long groupId){
                if(gRepository.existsById(groupId)){
                    Group g = gRepository.findById(groupId).get();
                    return new ResponseEntity(g, HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Group #" + groupId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@PostMapping("/group")
	public ResponseEntity createBodega(@Valid @RequestBody Group group) {
            try{
                    Group g = gRepository.save(group);
                    return new ResponseEntity(g, HttpStatus.CREATED);
            }
            catch(Exception e){
                    return new ResponseEntity<String>("It's not possible create new Group", HttpStatus.NOT_FOUND);
                
            }
	}

        @PutMapping("/group/{id}")
	public ResponseEntity updateBodega(
			@PathVariable(value = "id") Long groupId,
			@Valid @RequestBody Group groupDetails){
            try{
                if(gRepository.existsById(groupId)) {
                    Group group = gRepository.findById(groupId).get();
                    group.setName(groupDetails.getName());
                    final Group updatedGroup = gRepository.save(group);  
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Group #" + groupId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update "
                        + "Group #" + groupId, HttpStatus.NOT_FOUND);
                }
	}

        @DeleteMapping("/group/{id}")
	public ResponseEntity deleteBodega(
                                @PathVariable(value = "id") Long groupId){
                if(gRepository.existsById(groupId)){
                    Group g = gRepository.findById(groupId).get();
                    for(Device d: g.getDevices()){
                            d.setGroup(null);
                            deviceRepository.save(d);
                        }
                    for(Family f: g.getFamilies()){
                            f.setGroup(null);
                            familyRepository.save(f);
                        }
                    gRepository.delete(g);
                    return new ResponseEntity(HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<String>("Group #" + groupId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
            
	}
}
