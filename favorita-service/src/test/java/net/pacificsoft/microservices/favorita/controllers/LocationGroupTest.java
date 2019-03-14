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


    @Test
    public void added_expected_ok() throws Exception{
        LocationGroup locationGroup = new LocationGroup("Favorita");

        List<LocationGroup> locationGroups = Arrays.asList(locationGroup);

        //given(repository.findAll()).willReturn(locationGroups);

        JSONObject json = new JSONObject();
        json.put("name", locationGroup.getName());

        mvc.perform(post("/locationGroup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test

    public void givenLocationGroup_whenGetLocationGroup_thenReturnJSONArray() throws Exception{
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

    @Test
    public void update_expected_ok() throws Exception{
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

    @Test
    public void delete_Expected_ok() throws Exception{
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
