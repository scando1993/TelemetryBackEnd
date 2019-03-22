package net.pacificsoft.microservices.favorita.controllers;

import jdk.nashorn.internal.parser.JSONParser;
import net.pacificsoft.microservices.favorita.Application;
import net.pacificsoft.microservices.favorita.models.LocationGroup;
import net.pacificsoft.microservices.favorita.models.LocationPriority;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.LocationGroupRepository;
import net.pacificsoft.microservices.favorita.repository.LocationPriorityRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import org.json.JSONException;
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
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationPriorityController.class)
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
    public void create_test() throws Exception{
        LocationPriority locationPriority = new LocationPriority("locationP",1);
        Tracking t = new Tracking("a",new Date(2132));


        given(repositoryT.existsById(locationPriority.getId())).willReturn(true);
        given(repositoryT.findById(any())).willReturn(Optional.of(t));

        JSONObject json = new JSONObject();
        json.put("name", locationPriority.getName());
        json.put("priority", locationPriority.getPriority());


        mvc.perform(post("/locationPriority/{trackingid}", t.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getAll_test() throws Exception{
        LocationPriority locationPriority = new LocationPriority("LocationPrioruty1",2);
        LocationPriority locationPriority2 = new LocationPriority("LocationPrioruty2",22);
        Tracking tracking = new Tracking("track1",new Date(21321));
        Tracking tracking2 = new Tracking("track2",new Date(3244323));
        tracking.getLocationPrioritys().add(locationPriority);
        locationPriority.setTracking(tracking);
        List<LocationPriority> locationPriorities = Arrays.asList(locationPriority,locationPriority2);

        given(repository.findAll()).willReturn(locationPriorities);

        mvc.perform(get("/locationPriority")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].name", is(locationPriority2.getName())));
    }

    @Test
    public void getById_test() throws Exception{
        LocationPriority locationPriority = new LocationPriority("LocationPrioruty1",2);
        Tracking tracking = new Tracking("track1",new Date(21321));
        Tracking tracking2 = new Tracking("track2",new Date(3244323));
        tracking.getLocationPrioritys().add(locationPriority);
        locationPriority.setTracking(tracking);

        given(repository.existsById(locationPriority.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(locationPriority));

        mvc.perform(get("/locationPriority/"+locationPriority.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    
    @Test
    public void update_test() throws Exception{
        LocationPriority locationPriority = new LocationPriority("locationPriority", 212);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(locationPriority));

        JSONObject json = new JSONObject();
        json.put("name", locationPriority.getName());
        json.put("priority", locationPriority.getPriority());

        mvc.perform(put("/locationPriority/{id}", locationPriority.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void delete_test() throws Exception{
        LocationPriority locationPriority = new LocationPriority("LocationPriority", 99);
        Tracking tracking = new Tracking("track1",new Date(21321));
        tracking.getLocationPrioritys().add(locationPriority);
        locationPriority.setTracking(tracking);
        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(locationPriority));

        mvc.perform(delete("/locationPriority/{id}", locationPriority.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }


}
