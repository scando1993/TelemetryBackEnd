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
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import net.pacificsoft.microservices.favorita.repository.TelemetriaRepository;
import net.pacificsoft.microservices.favorita.repository.WifiScanRepository;

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
@WebMvcTest(TelemetriaController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class TelemetriaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private TelemetriaRepository repository;
    
    @MockBean
    @Autowired
    private DeviceRepository repositoryG;
    
    @Test
    public void getAll_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        Telemetria telemetria1 = new Telemetria(date1,"wf1",2);
        Telemetria telemetria2 = new Telemetria(date1,"wf2",2);
        List<Telemetria> TelemetriaList = Arrays.asList(telemetria1, telemetria2);
        
        given(repository.findAll()).willReturn(TelemetriaList);

        mvc.perform(get("/telemetria")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(telemetria1.getName())));
    }
    
    //getById
    @Test
    public void getById_test() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        Telemetria telemetria = new Telemetria(date1,"wf1",2);
        
        given(repository.existsById(telemetria.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(telemetria));
 
         mvc.perform(get("/telemetria/"+telemetria.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    //create
    @Test
    public void create_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	String j = sdf.format(new Date(448844658));
        Telemetria telemetria = new Telemetria(new Date(448844658),"wf1",2);
        
        Device device= new Device();
        telemetria.setDevice(device);
        device.getTelemetrias().add(telemetria);     

        given(repositoryG.existsById(telemetria.getDevice().getId())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(device));

        JSONObject json = new JSONObject();
        json.put("Dtm", j);
        json.put("name", telemetria.getName());
        json.put("value", telemetria.getValue());
        mvc.perform(post("/telemetria/"+telemetria.getDevice().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    //delete
    @Test
    public void delete_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        Telemetria telemetria = new Telemetria(date1,"wf1",2);
        
        Device device= new Device();
        telemetria.setDevice(device);
        device.getTelemetrias().add(telemetria);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(telemetria));
        
        mvc.perform(delete("/telemetria/"+ telemetria.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    //update
    @Test
    public void update_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	String j = sdf.format(new Date(448844658));
        Telemetria telemetria = new Telemetria(new Date(448844658),"wf1",2);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(telemetria));

        JSONObject json = new JSONObject();
        json.put("Dtm", j);
        json.put("name", telemetria.getName());
        json.put("value", telemetria.getValue());

        mvc.perform(put("/telemetria/"+telemetria.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}