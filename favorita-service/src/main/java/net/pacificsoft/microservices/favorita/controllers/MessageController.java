package net.pacificsoft.microservices.favorita.controllers;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.repository.*;
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
    public ResponseEntity getAllMessages() {
        try{

            List<Message> messages = messageRepository.findAll();

            return new ResponseEntity(messages, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/message/{id}")
    public ResponseEntity getAllMessages(@PathVariable(value = "id") Long idM) {
        try{
            return new ResponseEntity(messageRepository.findById(idM).get(), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/message/{predictionID}/{guessID}/{locationNameID}")
    public ResponseEntity createMessages(@PathVariable(value = "predictionID") Long predictionID,
                                       @PathVariable(value = "locationNameID") Long locationNameId,
                                       @PathVariable(value = "guessID") Long messageGuessID) {
        try{
            if(predictionsRepository.existsById(predictionID) && locationNamesRepository.existsById(locationNameId) && messageGuessRepository.existsById(messageGuessID)){
                Prediction prediction = predictionsRepository.findById(predictionID).get();
                LocationNames locationName = locationNamesRepository.findById(locationNameId).get();
                MessageGuess messageGuess = messageGuessRepository.findById(messageGuessID).get();
                Message message = new Message();
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
    
    @PutMapping("/message/{id}/{predictionID}/{guessID}/{locationNameID}")
	public ResponseEntity updateMessages(
			@PathVariable(value = "id") Long mId,
                        @PathVariable(value = "predictionID") Long predictionID,
                        @PathVariable(value = "locationNameID") Long locationNameId,
                        @PathVariable(value = "guessID") Long messageGuessID){
            try{
                if(messageRepository.existsById(mId) &&
                   predictionsRepository.existsById(predictionID) && 
                   locationNamesRepository.existsById(locationNameId) &&
                   messageGuessRepository.existsById(messageGuessID)){
                    Message message = messageRepository.findById(mId).get();
                    Prediction prediction = predictionsRepository.findById(predictionID).get();
                    LocationNames locationName = locationNamesRepository.findById(locationNameId).get();
                    MessageGuess messageGuess = messageGuessRepository.findById(messageGuessID).get();
                    
                    message.getPredictions().add(prediction);
                    message.setLocationNames(locationName);
                    message.setMessageGuess(messageGuess);

                    prediction.setMessage(message);
                    locationName.setMessage(message);
                    messageGuess.getMessages().add(message);

                    predictionsRepository.save(prediction);
                    locationNamesRepository.save(locationName);
                    messageGuessRepository.save(messageGuess);
                    final Message posted = messageRepository.save(message);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("It's not possible update Message #"+mId, HttpStatus.NOT_FOUND);
                }
            }
            catch(Exception e){
                return new ResponseEntity<String>("It's not possible update Message #" + mId, HttpStatus.NOT_FOUND);
            }
	}

	@DeleteMapping("/message/{id}")
	public ResponseEntity deleteMessages(
			@PathVariable(value = "id") Long mId){
                if(messageRepository.existsById(mId)){
                    Message message = messageRepository.findById(mId).get();
                    for(Prediction p: message.getPredictions()){
                        p.setMessage(null);
                        predictionsRepository.save(p);
                    }
                    message.getLocationNames().setMessage(null);
                    message.getMessageGuess().getMessages().remove(message);
                    locationNamesRepository.save(message.getLocationNames());
                    messageGuessRepository.save(message.getMessageGuess());
                    messageRepository.delete(message);
                    return new ResponseEntity(HttpStatus.OK);
                }
		else{
                    return new ResponseEntity<String>("Message #" + mId + 
                            " does not exist.", HttpStatus.NOT_FOUND);
                }
	}

}