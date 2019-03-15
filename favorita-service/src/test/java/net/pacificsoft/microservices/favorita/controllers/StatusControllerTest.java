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
import net.pacificsoft.microservices.favorita.models.Status;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.StatusRepository;

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
@WebMvcTest(StatusController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StatusControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private StatusRepository repository;
    
    @MockBean
    @Autowired
    private DeviceRepository repositoryG;
    
    @Test
    public void getAll_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        
        Status status1 = new Status(2,date1,23,date1);
        Status status2 = new Status(4,date1,23,date1);
        List<Status> statusList = Arrays.asList(status1, status2);
        
        given(repository.findAll()).willReturn(statusList);

        mvc.perform(get("/status")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].batery", is(status1.getBatery())));
    }
    
    //getById
    @Test
    public void getById_test() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	Date date1 = sdf.parse("1985-08-12T10:20");
        Status status = new Status(2,date1,23,date1);
        
        given(repository.existsById(status.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(status));
 
         mvc.perform(get("/status/"+status.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    //create
    @Test
    public void create_test() throws Exception{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	String j = sdf.format(new Date(448844658));
        Status status = new Status(2,new Date(448844658),23,new Date(448844658));
        
        Device device = new Device();
        status.setDevice(device);
        device.setStatus(status);     

        given(repositoryG.existsById(status.getDevice().getId())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(device));

        JSONObject json = new JSONObject();
        json.put("batery", status.getBatery());
        json.put("signal_level", status.getSignal_level());
        json.put("last_transmision", j);
        json.put("last_update", j);
        mvc.perform(post("/status/"+status.getDevice().getId())
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
        Status status = new Status(2,date1,23,date1);
        
        Device device = new Device();
        status.setDevice(device);
        device.setStatus(status); 

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(status));
        
        mvc.perform(delete("/status/"+ status.getId())
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
        Status status = new Status(2,new Date(448844658),23,new Date(448844658));

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(status));

        JSONObject json = new JSONObject();
        json.put("batery", status.getBatery());
        json.put("signal_level", status.getSignal_level());
        json.put("last_transmision", j);
        json.put("last_update", j);

        mvc.perform(put("/status/"+status.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}