package net.pacificsoft.microservices.favorita.controllers;
import java.util.List;
import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiGoResponseController {
    @Autowired
    private GoApiResponseRepository goRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/goApiResponse")
    public ResponseEntity getAllGoApiResponse() {
        try{

            List<GoApiResponse> goApiResponses = goRepository.findAll();

            return new ResponseEntity(goApiResponses, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/goApiResponse/{id}")
    public ResponseEntity getGoApiResponseById(@PathVariable(value = "id") Long goId) {
        try{

            GoApiResponse goApi = goRepository.findById(goId).get();

            return new ResponseEntity(goApi, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/goApiResponse/{messageID}/{deviceID}")
    public ResponseEntity createCiudad(@PathVariable(value = "messageID") Long messageID,
                                       @PathVariable(value = "deviceID") Long deviceID,
                                       @Valid @RequestBody GoApiResponse goApiResponse) {
        try{
            if(messageRepository.existsById(messageID) && deviceRepository.existsById(deviceID)){
                Message message = messageRepository.findById(messageID).get();
                Device device = deviceRepository.findById(deviceID).get();
                device.getGoApiResponses().add(goApiResponse);
                message.setGoApiResponse(goApiResponse);
                goApiResponse.setDevice(device);
                goApiResponse.setMessage(message);
                messageRepository.save(message);
                deviceRepository.save(device);
                GoApiResponse posted = goRepository.save(goApiResponse);

                return new ResponseEntity(posted, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<String>("Message #" + messageID + "or device #" +deviceID +
                        " does not exist, it's not possible create new apiGoResponse", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Prediction", HttpStatus.NOT_FOUND);

        }
    }
    
        @PutMapping("/goApiResponse/{id}")
	public ResponseEntity updateDevice(
			@PathVariable(value = "id") Long goId,
			@Valid @RequestBody GoApiResponse goDetails){
                if(goRepository.existsById(goId)){
                    GoApiResponse goApi = goRepository.findById(goId).get();
                    goApi.setSucess(goDetails.getSucess());
                    final GoApiResponse updatedGo = goRepository.save(goApi);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("GoApiResponse #" + goId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

	@DeleteMapping("/goApiResponse/{id}")
	public ResponseEntity deleteDevice(
			@PathVariable(value = "id") Long goId){
                if(goRepository.existsById(goId)){
                    GoApiResponse goApi = goRepository.findById(goId).get();
                    goApi.getDevice().getGoApiResponses().remove(goApi);
                    goApi.getMessage().setGoApiResponse(null);
                    deviceRepository.save(goApi.getDevice());
                    messageRepository.save(goApi.getMessage());
                    return new ResponseEntity(HttpStatus.OK); 
                }
		else{
                    return new ResponseEntity<String>("GoApiResponse #" + goId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}
}
