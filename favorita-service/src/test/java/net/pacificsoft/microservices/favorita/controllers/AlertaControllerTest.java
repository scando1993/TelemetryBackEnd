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
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.WifiScan;
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
    /*
    //getById
    @Test
    public void findById_test() throws Exception {
        WifiScan found = new WifiScan(1,"wf2");
        
        given(repository.existsById(found.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(found));
 
         mvc.perform(get("/wifiScan/"+found.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    /*
    //create
    @Test
    public void create_test() throws Exception{
        WifiScan found = new WifiScan(1,"wf2");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        RawSensorData rw=new RawSensorData(2, 12, date1, "rw1");
        found.setRawSensorData(rw);
        rw.getWifiScans().add(found);
        List<WifiScan> wifiList = Arrays.asList(found);

        //given(repository.findAll()).willReturn(locationGroups);       

        given(repositoryG.existsById(found.getRawSensorData().getId())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(rw));

        JSONObject json = new JSONObject();
        json.put("mac", found.getMAC());
        json.put("rssi", found.getRSSI());
        mvc.perform(post("/wifiScan/"+found.getRawSensorData().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    //delete
    @Test
    public void delete_test() throws Exception{
        WifiScan found = new WifiScan(1,"wf2");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        RawSensorData rw=new RawSensorData(2, 12, date1, "rw1");
        found.setRawSensorData(rw);
        rw.getWifiScans().add(found);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(found));
        found.getRawSensorData().getWifiScans().remove(found);
        mvc.perform(delete("/wifiScan/"+ found.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    //update
    @Test
    public void update_test() throws Exception{
        WifiScan found = new WifiScan(1,"wf2");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(found));

        JSONObject json = new JSONObject();
        json.put("mac", found.getMAC());
        json.put("rssi", found.getRSSI());

        mvc.perform(put("/wifiScan/"+found.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    */
}
 