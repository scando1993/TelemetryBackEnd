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

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MessageController {

    @Autowired
    private PredictionsRepository predictionsRepository;
    @Autowired
    private MessageGuessRepository messageGuessRepository;
    @Autowired
    private LocationNamesRepository locationNamesRepository;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/message")
    public ResponseEntity getAllmessages() {
        try{

            List<Message> messages = messageRepository.findAll();

            return new ResponseEntity(messages, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/message/{predictionID}/{guessID}/{locationNameID}")
    public ResponseEntity createCiudad(@PathVariable(value = "predictionID") Long predictionID,
                                       @PathVariable(value = "locationNameID") Long locationNameId,
                                       @PathVariable(value = "guessID") Long messageGuessID,
                                       @Valid @RequestBody Message message) {
        try{
            if(predictionsRepository.exists(predictionID) && locationNamesRepository.exists(locationNameId) && messageGuessRepository.exists(messageGuessID)){
                Prediction prediction = predictionsRepository.findOne(predictionID);
                LocationNames locationName = locationNamesRepository.findOne(locationNameId);
                MessageGuess messageGuess = messageGuessRepository.findOne(messageGuessID);

                message.getPredictions().add(prediction);
                message.setLocationNames(locationName);
                message.setMessageGuess(messageGuess);

                prediction.setMessage(message);
                locationName.setMessage(message);
                messageGuess.getMessages().add(message);

                predictionsRepository.save(prediction);
                locationNamesRepository.save(locationName);
                messageGuessRepository.save(messageGuess);
                Message posted = messageRepository.save(message);

                return new ResponseEntity(posted, HttpStatus.CREATED);
            }
            else{
                return new ResponseEntity<String>("Prediction #" + predictionID + "or LocationName #" +locationNameId + "0r MessageGuess #" + messageGuessID +
                        " does not exist, it's not possible create new Prediction", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible create new Prediction", HttpStatus.NOT_FOUND);

        }
    }

}
