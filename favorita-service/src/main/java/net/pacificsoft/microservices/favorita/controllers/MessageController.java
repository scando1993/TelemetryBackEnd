package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.LocationNames;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.models.MessageGuess;
import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageGuessRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
