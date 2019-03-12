package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.GoApiResponse;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.GoApiResponseRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiGoResponseController {
    @Autowired
    private GoApiResponseRepository goApiResponseRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/goApiResponse")
    public ResponseEntity getAllGoApiResponse() {
        try{

            List<GoApiResponse> goApiResponses = goApiResponseRepository.findAll();

            return new ResponseEntity(goApiResponses, HttpStatus.OK);
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
            if(messageRepository.exists(messageID) && deviceRepository.exists(deviceID)){
                Message message = messageRepository.findOne(messageID);
                Device device = deviceRepository.findOne(deviceID);

                // device.setGoApiResponse(setGoApiResponse);
                message.setGoApiResponse(goApiResponse);

                messageRepository.save(message);
                // deviceRepository.save(device);
                GoApiResponse posted = goApiResponseRepository.save(goApiResponse);

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
}
