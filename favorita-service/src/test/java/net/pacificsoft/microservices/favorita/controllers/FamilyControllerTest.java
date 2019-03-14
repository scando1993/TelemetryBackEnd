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
    public void givenAlertas_whenGetAlertas_thenReturnJSONArray() throws Exception{
        Family f1 = new Family("f1");

        Family f2 = new Family("f2");

        List<Family> deviceList = Arrays.asList(f1, f2);

        given(repository.findAll()).willReturn(deviceList);

        mvc.perform(get("/family")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is(f2.getName())));
    }
    
    /*@Test
    public void add_Device() throws Exception {
        Device device1 = new Device("f1", "n1", new Group("g1"));
        JSONObject json = new JSONObject();
        repository.save(device1);
        json.put("Id", device1.getId());
        json.put("group", device1.getGroup().getName());
        json.put("family", device1.getFamily());
        mvc.perform(post("/device")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].family", is(device1.getFamily())));
    }
*/
}