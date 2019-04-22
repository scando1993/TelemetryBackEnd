package net.pacificsoft.microservices.favorita.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import net.minidev.json.JSONObject;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import net.pacificsoft.microservices.favorita.repository.WifiScanRepository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WifiScanController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WifiScanControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private WifiScanRepository repository;
    
    @MockBean
    @Autowired
    private RawSensorDataRepository repositoryG;
    
    @Test
    public void getAll_test() throws Exception{
        WifiScan wf1 = new WifiScan(1,"wf1");
        WifiScan wf2 = new WifiScan(2,"wf2");
        List<WifiScan> wifiList = Arrays.asList(wf1, wf2);
        
        given(repository.findAll()).willReturn(wifiList);

        mvc.perform(get("/wifiScan")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].mac", is(wf1.getMac())));
    }
    
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
        json.put("mac", found.getMac());
        json.put("rssi", found.getRssi());
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
        json.put("mac", found.getMac());
        json.put("rssi", found.getRssi());

        mvc.perform(put("/wifiScan/"+found.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}