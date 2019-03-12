package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.models.MessageGuess;
import net.pacificsoft.microservices.favorita.repository.MessageGuessRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class MessageGuessController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private MessageGuessRepository messageGuessRepository;

    @GetMapping("/messageGuess")
    public ResponseEntity getAllMessageGuesses() {
        try{

            List<MessageGuess> messageGuesses = messageGuessRepository.findAll();

            return new ResponseEntity(messageGuesses, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>("Resources not available.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/messageGuess")
    public ResponseEntity createFurgon(@Valid @RequestBody MessageGuess messageGuess) {
        try{
            MessageGuess p = messageGuessRepository.save(messageGuess);
            return new ResponseEntity(p, HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<String>("New Probabilities not created.",
                    HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/messageGuess/{id}")
    public ResponseEntity updateFurgon(
            @PathVariable(value = "id") Long messageGuessId,
            @Valid @RequestBody MessageGuess messageGuessDetails){
        try{
            if(messageGuessRepository.exists(messageGuessId)){
                MessageGuess messageGuess = messageGuessRepository.findOne(messageGuessId);
                messageGuess.setLocation(messageGuessDetails.getLocation());
                messageGuess.setProbability(messageGuessDetails.getProbability());
                final MessageGuess updatemessageGuess = messageGuessRepository.save(messageGuess);
                return new ResponseEntity(HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("messageGuess #" + messageGuessId +
                        " does not exist.", HttpStatus.NOT_FOUND);
            }
        }
        catch(Exception e){
            return new ResponseEntity<String>("It's not possible update messageGuess #" + messageGuessId, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/messageGuess/{id}")
    public ResponseEntity deletemessageGuess(
            @PathVariable(value = "id") Long messageGuessId){
        if(messageGuessRepository.exists(messageGuessId)){
            MessageGuess messageGuess = messageGuessRepository.findOne(messageGuessId);
            if(messageGuess.getMessages().size()>0){
                Set<Message> m = messageGuess.getMessages();
                for (Message i : m){
                    i.setMessageGuess(null);
                    messageRepository.save(i);
                }
            }
            messageGuessRepository.delete(messageGuess);
            return new ResponseEntity(HttpStatus.OK);

        }
        else{
            return new ResponseEntity<String>("Furgon #" + messageGuessId +
                    " does not exist.", HttpStatus.NOT_FOUND);
        }
    }
}
