package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import net.minidev.json.JSONObject;
import net.pacificsoft.microservices.favorita.models.LocationNames;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.models.MessageGuess;
import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.models.Probabilities;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.FeaturesRepository;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageGuessRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import net.pacificsoft.microservices.favorita.repository.ProbabilitiesRepository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RunWith(SpringRunner.class)
@WebMvcTest(ProbabilitiesController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ProbabilitiesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private ProbabilitiesRepository repository;
    
    @MockBean
    @Autowired
    private PredictionsRepository repositoryP;
    
    @Test
    public void getAll_test() throws Exception{
        Probabilities probability1= new Probabilities(1.1,1.2);
        Probabilities probability2= new Probabilities(3.3,2.2);

        List<Probabilities> ProbabilitiesList = Arrays.asList(probability1, probability2);

        given(repository.findAll()).willReturn(ProbabilitiesList);
        mvc.perform(get("/probability")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].probability", is(probability1.getProbability())));
    }
    
    @Test
    public void getById_test() throws Exception {
        Probabilities probability= new Probabilities(3.3,1.2);
        Prediction prediction = new Prediction("p");
        probability.setPrediction(prediction);
        prediction.getProbabilitieses().add(probability);
        
        given(repository.existsById(probability.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(probability));
 
         mvc.perform(get("/probability/"+probability.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void create_test() throws Exception{
        Probabilities probability= new Probabilities(3.3,1.2);
        Prediction prediction = new Prediction("p");
        probability.setPrediction(prediction);
        prediction.getProbabilitieses().add(probability);
       
        JSONObject json = new JSONObject();
        json.put("name", probability.getNameID());
        json.put("probability", probability.getProbability());
        
        given(repository.existsById(probability.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(probability));
        
        given(repositoryP.existsById(probability.getPrediction().getId())).willReturn(true);
        given(repositoryP.findById(any())).willReturn(Optional.of(prediction));
        
        mvc.perform(post("/probability/"+probability.getPrediction().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    @Test
    public void delete_test() throws Exception{
        Probabilities probability= new Probabilities(3.3,1.2);
        Prediction prediction = new Prediction("p");
        probability.setPrediction(prediction);
        prediction.getProbabilitieses().add(probability);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(probability));
        
        mvc.perform(delete("/probability/"+ probability.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void update_test() throws Exception{
        Probabilities probability= new Probabilities(3.3,1.2);
        Prediction prediction = new Prediction("p");
        probability.setPrediction(prediction);
        prediction.getProbabilitieses().add(probability);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(probability));

        JSONObject json = new JSONObject();
        json.put("name", probability.getNameID());
        json.put("probability", probability.getProbability());

        mvc.perform(put("/probability/"+probability.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}
 