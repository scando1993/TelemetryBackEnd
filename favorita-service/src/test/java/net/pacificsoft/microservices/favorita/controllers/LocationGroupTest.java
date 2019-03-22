package net.pacificsoft.microservices.favorita.controllers;

import jdk.nashorn.internal.parser.JSONParser;
import net.pacificsoft.microservices.favorita.Application;
import net.pacificsoft.microservices.favorita.models.LocationGroup;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.LocationGroupRepository;
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
import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationGroupController.class)
public class LocationGroupTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private LocationGroupRepository repository;

    @MockBean
    @Autowired
    private TrackingRepository repositoryT;

    //ok
    @Test
    public void create_test() throws Exception{
        LocationGroup locationGroup = new LocationGroup("Favorita");

        List<LocationGroup> locationGroups = Arrays.asList(locationGroup);

        JSONObject json = new JSONObject();
        json.put("name", locationGroup.getName());

        mvc.perform(post("/locationGroup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //ok
    @Test
    public void getAll_test() throws Exception{
        LocationGroup locationGroup1 = new LocationGroup("Favorita");
        Tracking tracking = new Tracking("k",new Date(21321));
        Tracking tracking2 = new Tracking("allan",new Date(3244323));
        locationGroup1.getTrackings().add(tracking);
        locationGroup1.getTrackings().add(tracking2);
        List<LocationGroup> alertList = Arrays.asList(locationGroup1);

        given(repository.findAll()).willReturn(alertList);

        mvc.perform(get("/locationGroup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }
    //ok
    @Test
    public void getById_test() throws Exception{
        LocationGroup locationGroup1 = new LocationGroup("Favorita");
        Tracking tracking = new Tracking("k",new Date(21321));
        Tracking tracking2 = new Tracking("allan",new Date(3244323));
        locationGroup1.getTrackings().add(tracking);
        locationGroup1.getTrackings().add(tracking2);

        given(repository.existsById(locationGroup1.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(locationGroup1));

        mvc.perform(get("/locationGroup/"+locationGroup1.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());

    }
    //ok
    @Test
    public void update_test() throws Exception{
        LocationGroup locationGroup = new LocationGroup("FavoritaUpdated");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(locationGroup));

        JSONObject json = new JSONObject();
        json.put("name", "FavoritaUpdated2");

        mvc.perform(put("/locationGroup/{id}", locationGroup.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());

    }
    //ok
    @Test
    public void delete_test() throws Exception{
        LocationGroup locationGroup = new LocationGroup("FavoritaUpdated");

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(locationGroup));

        mvc.perform(delete("/locationGroup/{id}", locationGroup.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
