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
import net.pacificsoft.microservices.favorita.models.Family;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.FamilyRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import org.json.JSONObject;
import static org.mockito.BDDMockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GroupController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private FamilyRepository repositoryF;
    
    @MockBean
    @Autowired
    private GroupRepository repositoryG;
    
    @MockBean
    @Autowired
    private DeviceRepository repositoryD;

    @Test
    public void getAll_test() throws Exception{
        Group g1 = new Group("g1");
        Group g2 = new Group("g1");   
        List<Group> deviceList = Arrays.asList(g1, g2);

        given(repositoryG.findAll()).willReturn(deviceList);

        mvc.perform(get("/group")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(g1.getName())));
    }
    
    
    @Test
    public void getById_test() throws Exception{
        Group g1 = new Group("g1");
        given(repositoryG.existsById(g1.getId())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(g1));
        mvc.perform(get("/group/"+g1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void post_test() throws Exception {
        Group g1 = new Group("g1");
        JSONObject json = new JSONObject();
        json.put("name", g1.getName());
        mvc.perform(post("/group")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    
    @Test
    public void delete_test() throws Exception{
        Group g1 = new Group("g1");
        given(repositoryG.existsById(any())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(g1));

        mvc.perform(delete("/group/{id}", g1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void update_test() throws Exception{
        Group g1 = new Group("g1");

        given(repositoryG.existsById(any())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(g1));

        JSONObject json = new JSONObject();
        json.put("name", g1.getName());

        mvc.perform(put("/group/"+g1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
}