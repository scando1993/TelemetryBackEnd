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
import net.minidev.json.JSONObject;
import net.pacificsoft.microservices.favorita.models.LocationNames;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.models.Probabilities;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;
import net.pacificsoft.microservices.favorita.repository.ProbabilitiesRepository;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PredictionController.class)
public class PredictionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private PredictionsRepository repository;
    
    @MockBean
    @Autowired
    private ProbabilitiesRepository repositoryP;
    
    @MockBean
    @Autowired
    private LocationNamesRepository repositoryL;
    
    @MockBean
    @Autowired
    private MessageRepository repositoryM;
    

    @Test
    public void getAll_test() throws Exception{
        Prediction prediction1= new Prediction("n1");
        Prediction prediction2= new Prediction("n2");
                
        List<Prediction> predictionList = Arrays.asList(prediction1, prediction2);

        given(repository.findAll()).willReturn(predictionList);

        mvc.perform(get("/prediction")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(prediction1.getName())));
    }
    
    
    @Test
    public void getById_test() throws Exception{
        Prediction prediction= new Prediction("n1");
        //Probabilities prob= new Probabilities();
        //LocationNames ln= new LocationNames();
        
        given(repository.existsById(prediction.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(prediction));
        mvc.perform(get("/prediction/"+prediction.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void create_test() throws Exception {
        Prediction prediction= new Prediction("n1");
//        Probabilities prob= new Probabilities();
//        LocationNames ln= new LocationNames();
//        prediction.setLocationNames(ln);
//        ln.getPrediction().add(prediction);
//        prediction.setProbabilities(prob);
//        prob.getPrediction().add(prediction);
        
        JSONObject json = new JSONObject();
        json.put("name", prediction.getName());
        
        mvc.perform(post("/prediction")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toJSONString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    
    @Test
    public void delete_test() throws Exception{
        Prediction prediction= new Prediction("n1");
//        Probabilities prob= new Probabilities();
//        LocationNames ln= new LocationNames();
//        prediction.setLocationNames(ln);
//        ln.getPrediction().add(prediction);
//        prediction.setProbabilities(prob);
//        prob.getPrediction().add(prediction);
        
        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(prediction));

        mvc.perform(delete("/prediction/{id}", prediction.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void update_test() throws Exception{
        Prediction prediction= new Prediction("n1");
//        Probabilities prob= new Probabilities();
//        LocationNames ln= new LocationNames();
//        prediction.setLocationNames(ln);
//        ln.getPrediction().add(prediction);
//        prediction.setProbabilities(prob);
//        prob.getPrediction().add(prediction);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(prediction));

        JSONObject json = new JSONObject();
        json.put("name", prediction.getName());

        mvc.perform(put("/prediction/{id}", prediction.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}