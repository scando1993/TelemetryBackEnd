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
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import net.pacificsoft.microservices.favorita.repository.WifiScanRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;

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
@WebMvcTest(AlertaController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AlertaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private AlertaRepository repository;
    
    @MockBean
    @Autowired
    private DeviceRepository repositoryD;
    
    @MockBean
    @Autowired
    private RutaRepository repositoryR;
    
    @Test
    public void getAll_test() throws Exception{
        
        Alerta alert1 = new Alerta("1", "Message 1");
        Alerta alert2 = new Alerta("2", "Message 2");

        List<Alerta> alertList = Arrays.asList(alert1, alert2);

        given(repository.findAll()).willReturn(alertList);

        mvc.perform(get("/alerta")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type_alert", is(alert1.getType_alert())));
    }
    
    @Test
    public void getById_test() throws Exception {
        Alerta alert = new Alerta("1", "Message 1");
        
        given(repository.existsById(alert.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(alert));
 
         mvc.perform(get("/alerta/"+alert.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void create_test() throws Exception{
        Device device= new Device();
        Ruta ruta= new Ruta();
        Alerta alert = new Alerta(1,"1", "Message 1",ruta,device);

        given(repositoryD.existsById(alert.getDevice().getId())).willReturn(true);
        given(repositoryD.findById(any())).willReturn(Optional.of(device));
        given(repositoryR.existsById(alert.getRuta().getId())).willReturn(true);
        given(repositoryR.findById(any())).willReturn(Optional.of(ruta));

        JSONObject json = new JSONObject();
        json.put("message", alert.getMensaje());
        json.put("type_alert", alert.getType_alert());
        mvc.perform(post("/alerta/"+alert.getRuta().getId()+'/'+alert.getDevice().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
     
    
    @Test
    public void delete_test() throws Exception{
        Alerta alert = new Alerta("1", "Message 1");
        Device d= new Device();
        Ruta r= new Ruta();
        alert.setDevice(d);
        alert.setRuta(r);
        d.getAlertas().add(alert);
        r.getAlertas().add(alert);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(alert));
        
        mvc.perform(delete("/alerta/"+ alert.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void update_test() throws Exception{
        Device device= new Device();
        Ruta ruta= new Ruta();
        Alerta alert = new Alerta(1,"1", "Message 1",ruta,device);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(alert));

        JSONObject json = new JSONObject();
        json.put("message", alert.getMensaje());
        json.put("type_alert", alert.getType_alert());

        mvc.perform(put("/alerta/"+alert.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}
 