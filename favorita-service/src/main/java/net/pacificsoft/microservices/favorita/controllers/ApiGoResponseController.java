package net.pacificsoft.springbootcrudrest.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import net.pacificsoft.springbootcrudrest.model.*;
import net.pacificsoft.springbootcrudrest.repository.*;
import org.json.JSONObject;
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

import net.pacificsoft.springbootcrudrest.repository.LocationNamesRepository;

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
