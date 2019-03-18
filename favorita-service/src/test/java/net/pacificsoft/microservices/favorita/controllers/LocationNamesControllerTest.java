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
import net.pacificsoft.microservices.favorita.models.Prediction;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.FeaturesRepository;
import net.pacificsoft.microservices.favorita.repository.LocationNamesRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import net.pacificsoft.microservices.favorita.repository.PredictionsRepository;

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
@WebMvcTest(LocationNamesController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class LocationNamesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private LocationNamesRepository repository;
    
    @MockBean
    @Autowired
    private PredictionsRepository repositoryM;
    
    @Test
    public void getAll_test() throws Exception{
        //Message ms=new Message();
        LocationNames ln1 = new LocationNames(new Double(11),"m");
        LocationNames ln2 = new LocationNames(2.2,"m");

        List<LocationNames> locationNamesList = Arrays.asList(ln1, ln2);

        given(repository.findAll()).willReturn(locationNamesList);
        mvc.perform(get("/locationNames")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(ln1.getName())));
    }
    
    @Test
    public void getById_test() throws Exception {
        LocationNames ln = new LocationNames(1.1,"m");
        
        given(repository.existsById(ln.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(ln));
 
         mvc.perform(get("/locationNames/"+ln.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void create_test() throws Exception{
        LocationNames ln = new LocationNames(1.3,"m");
       
        JSONObject json = new JSONObject();
        json.put("idname", ln.getIdname());
        json.put("name", ln.getName());
        
        mvc.perform(post("/locationNames")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    @Test
    public void delete_test() throws Exception{
        LocationNames ln = new LocationNames(1.1,"m");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(ln));
        
        mvc.perform(delete("/locationNames/"+ ln.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void update_test() throws Exception{
        LocationNames ln = new LocationNames(1.1,"m");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(ln));

        JSONObject json = new JSONObject();
        json.put("idname", ln.getIdname());
        json.put("name", ln.getName());

        mvc.perform(put("/locationNames/"+ln.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}
 