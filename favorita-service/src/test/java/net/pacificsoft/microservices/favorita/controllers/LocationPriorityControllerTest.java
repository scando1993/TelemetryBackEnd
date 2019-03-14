package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.Application;
import net.pacificsoft.microservices.favorita.models.LocationGroup;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.LocationGroupRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.json.JSONObject;
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
import java.util.Date;
import java.util.List;
import net.pacificsoft.microservices.favorita.repository.LocationPriorityRepository;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
public class LocationPriorityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private LocationPriorityRepository repository;

    @MockBean
    @Autowired
    private TrackingRepository repositoryT;

    @Test
    public void added_expected_ok() throws Exception{
        long locationPriorityID = 1;
        long tarckingID = 1;
        String name = "locationPriority1";
        int priority = 1;

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("priority", priority);


        mvc.perform(post("/tracking/{tarckingid}",tarckingID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", is(locationPriorityID)))
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.priority", is(priority)));
    }

    @Test

    public void givenLocationGroup_whenGetLocationGroup_thenReturnJSONArray() throws Exception{
        long locationPriorityID = 1;
        long tarckingID = 1;
        String name = "locationPriority1";
        int priority = 1;

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("priority", priority);


        mvc.perform(get("/tracking")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(locationPriorityID)))
                .andExpect(jsonPath("$[0].name", is(name)))
                .andExpect(jsonPath("$[0].priority", is(priority)));
    }

    @Test
    public void update_expected_ok() throws Exception{
        long locationPriorityID = 1;
        long tarckingID = 1;
        String name = "locationPriorityUpdated";
        int priority = 3;

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("priority", priority);

        mvc.perform(post("/locationPriority/{id}", locationPriorityID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void delete_Expected_ok() throws Exception{
        long locationPriorityID = 1;
        mvc.perform(delete("/locationPriority/{id}", locationPriorityID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
