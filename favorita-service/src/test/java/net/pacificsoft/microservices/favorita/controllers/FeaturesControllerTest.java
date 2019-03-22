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
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Features;
import net.pacificsoft.microservices.favorita.repository.FeaturesRepository;

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
@WebMvcTest(FeatureController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FeaturesControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private FeaturesRepository repository;
    
    
    @Test
    public void getAll_test() throws Exception{
        
        Features features1 = new Features(true,"1","m","i","r","c","o", 2,true);
        Features features2 = new Features(false,"2","m","i","r","c","o", 2,true);

        List<Features> featuresList = Arrays.asList(features1, features2);

        given(repository.findAll()).willReturn(featuresList);

        mvc.perform(get("/features")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].modelo", is(features1.getModelo())));
    }
    
    @Test
    public void getById_test() throws Exception {
        Features features = new Features(true,"1","m","t","r","c","o", 2,true);
        
        given(repository.existsById(features.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(features));
 
         mvc.perform(get("/features/"+features.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void create_test() throws Exception{
        Features features = new Features(true,"1","i","t","r","c","o", 2,true);
       
        JSONObject json = new JSONObject();
        json.put("alimentacion", features.getAlimentacion());
        json.put("firmware", features.getFirmware());
        json.put("modelo", features.getModelo());
        json.put("modos_transmision", features.getModos_transmision());
        json.put("nivel_voltaje", features.getNivel_voltaje());
        json.put("tipo_sensores", features.getTipo_sensores());
        json.put("version", features.getVersion());
        json.put("memoria", features.isMemoria());
        json.put("tracking", features.isTracking());
        
        mvc.perform(post("/features")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    @Test
    public void delete_test() throws Exception{
        Features features = new Features(true,"1","i","t","r","c","o", 2,true);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(features));
        
        mvc.perform(delete("/features/"+ features.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void update_test() throws Exception{
        Features features = new Features(true,"1","i","t","r","c","o", 2,true);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(features));

        JSONObject json = new JSONObject();
        json.put("alimentacion", features.getAlimentacion());
        json.put("firmware", features.getFirmware());
        json.put("modelo", features.getModelo());
        json.put("modos_transmision", features.getModos_transmision());
        json.put("nivel_voltaje", features.getNivel_voltaje());
        json.put("tipo_sensores", features.getTipo_sensores());
        json.put("version", features.getVersion());
        json.put("memoria", features.isMemoria());
        json.put("tracking", features.isTracking());

        mvc.perform(put("/features/"+features.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}
 