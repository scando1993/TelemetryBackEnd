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
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.SigfoxMessage;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;

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
@WebMvcTest(RawSensorDataController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RawSensorDataControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private RawSensorDataRepository repository;
    
    @MockBean
    @Autowired
    private DeviceRepository repositoryD;
    
    @Test
    public void getAll_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        Device d1=new Device();
        Device d2=new Device();
        RawSensorData rw1=new RawSensorData(1,1, 12, date1, "rw1",d1);
        RawSensorData rw2=new RawSensorData(2,2, 17, date1, "rw2",d2);
        List<RawSensorData> rawList = Arrays.asList(rw1, rw2);
        
        given(repository.findAll()).willReturn(rawList);

        mvc.perform(get("/rawSensorData")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rawData", is(rw1.getRawData())));
    }
    
    //getById
    @Test
    public void findById_test() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        Device d1=new Device();
        RawSensorData rw1=new RawSensorData(1,1, 12, date1, "rw1",d1);
        
        given(repository.existsById(rw1.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(rw1));
 
         mvc.perform(get("/rawSensorData/"+rw1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    //create
    @Test
    public void create_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	String j = sdf.format(new Date(448844658));
        Device d1 = new Device();
        RawSensorData rw=new RawSensorData(1, 12, new Date(448844658), "rw1");
        rw.setDevice(d1);
        d1.getRawSensorDatas().add(rw);
        List<RawSensorData> wifiList = Arrays.asList(rw);    

        given(repositoryD.existsById(rw.getDevice().getId())).willReturn(true);
        given(repositoryD.findById(any())).willReturn(Optional.of(d1));

        JSONObject json = new JSONObject();
        json.put("epoch", rw.getEpoch());
        json.put("epochDateTime", j);
        json.put("rawData", rw.getRawData());
        json.put("temperature", rw.getTemperature());
        mvc.perform(post("/rawSensorData/"+rw.getDevice().getId())
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
        Device d1= new Device();
        RawSensorData rw=new RawSensorData(2, 12, date1, "rw1");
        rw.setDevice(d1);
        d1.getRawSensorDatas().add(rw);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(rw));
        rw.getDevice().getRawSensorDatas().remove(rw);
        mvc.perform(delete("/rawSensorData/"+ rw.getId())
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
        RawSensorData rw=new RawSensorData(1, 12, new Date(448844658), "rw1");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(rw));

        JSONObject json = new JSONObject();
        json.put("epoch", rw.getEpoch());
        json.put("epochDateTime", j);
        json.put("rawData", rw.getRawData());
        json.put("temperature", rw.getTemperature());

        mvc.perform(put("/rawSensorData/"+rw.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}