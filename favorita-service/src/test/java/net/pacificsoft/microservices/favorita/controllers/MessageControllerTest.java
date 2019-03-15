package net.pacificsoft.microservices.favorita.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.pacificsoft.microservices.favorita.models.LocationNames;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.models.MessageGuess;
import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageGuessRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MessageController.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private MessageRepository repository;
    
    @MockBean
    @Autowired
    private PredictionsRepository repositoryP;
    
    @MockBean
    @Autowired
    private MessageGuessRepository repositoryG;
    
    @MockBean
    @Autowired
    private LocationNamesRepository repositoryL;

    @Test
    public void getAll_test() throws Exception{
        Message m1 = new Message();
        Message m2 = new Message();
        List<Message> messageList = Arrays.asList(m1, m2);

        given(repository.findAll()).willReturn(messageList);

        mvc.perform(get("/message")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
                //.andExpect(jsonPath("$[0].family", is(m1..getFamily())));
    }
    
    
    @Test
    public void getById_test() throws Exception{
        Message message = new Message();
        given(repository.existsById(message.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(message));
        mvc.perform(get("/message/"+message.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void post_test() throws Exception {
        Message message = new Message();
        Prediction prediction =new Prediction();
        MessageGuess mg= new MessageGuess();
        LocationNames ln=new LocationNames();
        
        message.setLocationNames(ln);
        message.setMessageGuess(mg);
        message.getPredictions().add(prediction);
        
        prediction.setMessage(message);
        ln.setMessage(message);
        mg.getMessages().add(message);
        
        given(repositoryL.existsById(message.getLocationNames().getId())).willReturn(true);
        given(repositoryL.findById(any())).willReturn(Optional.of(ln));
        
        given(repositoryP.existsById(prediction.getId())).willReturn(true);
        given(repositoryP.findById(any())).willReturn(Optional.of(prediction));
        
        given(repositoryG.existsById(message.getMessageGuess().getId())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(mg));
        
        mvc.perform(post("/message/"+prediction.getId()+"/"+mg.getId()+"/"+ln.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    
    @Test
    public void delete_test() throws Exception{
        Message message = new Message();
        Prediction prediction =new Prediction();
        MessageGuess mg= new MessageGuess();
        LocationNames ln=new LocationNames();
        
        message.setLocationNames(ln);
        message.setMessageGuess(mg);
        message.getPredictions().add(prediction);
        
        prediction.setMessage(message);
        ln.setMessage(message);
        mg.getMessages().add(message);
        
        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(message));

        mvc.perform(delete("/message/{id}", message.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void update_test() throws Exception{
        Message message = new Message();
        Prediction prediction =new Prediction();
        MessageGuess mg= new MessageGuess();
        LocationNames ln=new LocationNames();
        
        given(repositoryL.existsById(any())).willReturn(true);
        given(repositoryG.existsById(any())).willReturn(true);
        given(repositoryP.existsById(any())).willReturn(true);
        given(repository.existsById(any())).willReturn(true);

        given(repositoryL.findById(any())).willReturn(Optional.of(ln));
        given(repositoryG.findById(any())).willReturn(Optional.of(mg));
        given(repositoryP.findById(any())).willReturn(Optional.of(prediction));
        given(repository.findById(any())).willReturn(Optional.of(message));

        mvc.perform(put("/message/"+message.getId()+"/"+prediction.getId()+"/"+mg.getId()+"/"+ln.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}