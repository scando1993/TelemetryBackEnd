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
import static javax.swing.text.StyleConstants.Family;
import net.minidev.json.JSONObject;
import net.pacificsoft.microservices.favorita.controllers.application.BodegaController;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Family;
import net.pacificsoft.microservices.favorita.models.Group;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.GroupRepository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
public class GroupControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private GroupRepository repositoryG;
    
    @MockBean
    @Autowired
    private GroupRepository repository;
    
     @Autowired
    private TestRestTemplate template;
     
     private static String BOOK_ENDPOINT = "http://localhost:8080/family";
    private static String AUTHOR_ENDPOINT = "http://localhost:8080/authors";
    private static String ADDRESS_ENDPOINT = "http://localhost:8080/addresses";
    private static String LIBRARY_ENDPOINT = "http://localhost:8080/group";
 
    private static String LIBRARY_NAME = "My Library";
    private static String AUTHOR_NAME = "George Orwell";
    @Test
    public void givenAlertas_whenGetAlertas_thenReturnJSONArray() throws Exception{
        Group g = new Group("G1");
        template.postForEntity(LIBRARY_ENDPOINT, g, Group.class);

        Family f1 = new Family("f1");
        template.postForEntity(BOOK_ENDPOINT, f1, Family.class);

        Family f2 = new Family("f2");
        template.postForEntity(BOOK_ENDPOINT, f2, Family.class);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Content-Type", "text/uri-list");    
        HttpEntity<String> bookHttpEntity 
          = new HttpEntity<>(LIBRARY_ENDPOINT + "/1", requestHeaders);
        template.exchange(BOOK_ENDPOINT + "/1/library", 
          HttpMethod.PUT, bookHttpEntity, String.class);
        template.exchange(BOOK_ENDPOINT + "/2/library", 
          HttpMethod.PUT, bookHttpEntity, String.class);

        ResponseEntity<Library> libraryGetResponse = 
          template.getForEntity(BOOK_ENDPOINT + "/1/library", Library.class);
        assertEquals("library is incorrect", 
          libraryGetResponse.getBody().getName(), LIBRARY_NAME);
    }
    
   /* @Test
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
    }*/

}