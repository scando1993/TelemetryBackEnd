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
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.GoApiResponseRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private DeviceRepository repository;
    
    @MockBean
    @Autowired
    private TrackingRepository repositoryT;
    
    @MockBean
    @Autowired
    private GroupRepository repositoryG;
    
    @MockBean
    @Autowired
    private GoApiResponseRepository repositoryGo;

    @Test
    public void getAll_test() throws Exception{
        Device device1 = new Device("f1", "n1");
        Device device2 = new Device("f2", "n2");
        List<Device> deviceList = Arrays.asList(device1, device2);

        given(repository.findAll()).willReturn(deviceList);

        mvc.perform(get("/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].family", is(device1.getFamily())));
    }
    
    
    @Test
    public void getById_test() throws Exception{
        Device device1 = new Device("f1", "n1");
        given(repository.existsById(device1.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(device1));
        mvc.perform(get("/device/"+device1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void create_test() throws Exception {
        Device device1 = new Device("f1", "n1");
        Group g1 = new Group("g1");
        device1.setGroup(g1);
        g1.getDevices().add(device1);
        
        JSONObject json = new JSONObject();
        json.put("name", device1.getName());
        json.put("family", device1.getFamily());
        
        given(repositoryG.existsById(device1.getGroup().getId())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(g1));
        
        mvc.perform(post("/device/"+device1.getGroup().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toJSONString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    
    @Test
    public void delete_test() throws Exception{
        Device device1 = new Device("f1", "n1");
        Group g1 = new Group("g1");
        device1.setGroup(g1);
        g1.getDevices().add(device1);
        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(device1));

        mvc.perform(delete("/device/{id}", device1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void update_test() throws Exception{
        Device device1 = new Device("f1","n1");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(device1));

        JSONObject json = new JSONObject();
        json.put("name", device1.getName());
        json.put("family", device1.getFamily());

        mvc.perform(put("/device/{id}", device1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    
}