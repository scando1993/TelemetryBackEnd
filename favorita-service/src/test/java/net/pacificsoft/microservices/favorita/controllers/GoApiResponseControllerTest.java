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
import net.pacificsoft.microservices.favorita.models.GoApiResponse;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.models.Message;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.GoApiResponseRepository;
import net.pacificsoft.microservices.favorita.repository.MessageRepository;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ApiGoResponseController.class)
public class GoApiResponseControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private GoApiResponseRepository repository;
    
    @MockBean
    @Autowired
    private MessageRepository repositoryM;
    
    @MockBean
    @Autowired
    private DeviceRepository repositoryD;
/*
    @Test
    public void getAll_test() throws Exception{
        GoApiResponse goApi1 = new GoApiResponse(1);
        GoApiResponse goApi2 = new GoApiResponse(2);
        List<GoApiResponse> goApiList = Arrays.asList(goApi1, goApi2);

        given(repository.findAll()).willReturn(goApiList);

        mvc.perform(get("/goApiResponse")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].sucess", is(goApi1.getSucess())));
    }
    
    
    @Test
    public void getById_test() throws Exception{
        GoApiResponse goApi = new GoApiResponse(2);
        given(repository.existsById(goApi.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(goApi));
        
        mvc.perform(get("/goApiResponse/"+goApi.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void create_test() throws Exception {
        GoApiResponse goApi = new GoApiResponse(2);
        Message message=new Message();
        Device device =new Device();
        goApi.setMessage(message);
        goApi.setDevice(device);
        
        JSONObject json = new JSONObject();
        json.put("sucess", goApi.getSucess());
        
        given(repositoryM.existsById(goApi.getMessage().getId())).willReturn(true);
        given(repositoryM.findById(any())).willReturn(Optional.of(message));
        
        given(repositoryD.existsById(goApi.getDevice().getId())).willReturn(true);
        given(repositoryD.findById(any())).willReturn(Optional.of(device));
        
        mvc.perform(post("/goApiResponse/"+goApi.getMessage().getId()+'/'+goApi.getDevice().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toJSONString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    
    @Test
    public void delete_test() throws Exception{
        GoApiResponse goApi = new GoApiResponse(2);
        Message message=new Message();
        Device device =new Device();
        goApi.setMessage(message);
        goApi.setDevice(device);
        
        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(goApi));

        mvc.perform(delete("/goApiResponse/{id}", goApi.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void update_test() throws Exception{
        GoApiResponse goApi = new GoApiResponse(2);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(goApi));

        JSONObject json = new JSONObject();
        json.put("sucess", goApi.getSucess());

        mvc.perform(put("/goApiResponse/{id}", goApi.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
  */
}