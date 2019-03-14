package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.Application;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.minidev.json.JSONObject;
import net.pacificsoft.microservices.favorita.controllers.application.BodegaController;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Family;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.FamilyRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;

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

@RunWith(SpringRunner.class)
@WebMvcTest(FamilyController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class FamilyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private FamilyRepository repository;
    
    @MockBean
    @Autowired
    private GroupRepository repositoryG;

    @Test
    public void getAll_test() throws Exception{
        Family f1 = new Family("f1");
        Group g1 = new Group("g1");
        Group g2 = new Group("g1");        
        Family f2 = new Family("f2");
        f1.setGroup(g1);
        f2.setGroup(g2);
        g1.getFamilies().add(f1);
        g2.getFamilies().add(f1);
        repositoryG.save(g1);
        repositoryG.save(g2);
        repository.save(f1);
        repository.save(f2);
        List<Family> deviceList = Arrays.asList(f1, f2);

        given(repository.findAll()).willReturn(deviceList);

        mvc.perform(get("/family")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(f1.getName())));
    }
    
        @Test
    public void getById_test() throws Exception{
        Family f1 = new Family("f1");
        given(repository.existsById(f1.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(f1));
        mvc.perform(get("/family/"+f1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void post_test() throws Exception {
        Family f1 = new Family("f1");
        Group g1 = new Group("g1");
        f1.setGroup(g1);
        g1.getFamilies().add(f1);
        JSONObject json = new JSONObject();
        json.put("name", f1.getName());
        given(repositoryG.existsById(f1.getGroup().getId())).willReturn(true);
        given(repositoryG.findById(any())).willReturn(Optional.of(g1));
        mvc.perform(post("/family/"+f1.getGroup().getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toJSONString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    
    
    @Test
    public void delete_test() throws Exception{
        Family f1 = new Family("f1");
        Group g1 = new Group("g1");
        f1.setGroup(g1);
        g1.getFamilies().add(f1);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(f1));
        f1.getGroup().getFamilies().remove(f1);
        mvc.perform(delete("/family/{id}", f1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void update_test() throws Exception{
        Family f1 = new Family("f1");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(f1));

        JSONObject json = new JSONObject();
        json.put("name", f1.getName());

        mvc.perform(put("/family/"+f1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
}