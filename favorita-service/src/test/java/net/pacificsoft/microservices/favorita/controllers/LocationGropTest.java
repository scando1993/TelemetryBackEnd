package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.Application;
import net.pacificsoft.microservices.favorita.models.LocationGroup;
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
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationGroupController.class)
public class LocationGropTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocationGroupRepository repository;


    @Test
    public void added_expected_ok() throws Exception{
        LocationGroup locationGroup = new LocationGroup("Favorita");

        List<LocationGroup> locationGroups = Arrays.asList(locationGroup);

        given(repository.findAll()).willReturn(locationGroups);

        JSONObject json = new JSONObject();
        json.put("name", locationGroup.getName());

        mvc.perform(post("/locationGroup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.name", is(locationGroup.getName())));
    }

    @Test

    public void givenLocationGroup_whenGetLocationGroup_thenReturnJSONArray() throws Exception{
        LocationGroup locationGroup1 = new LocationGroup("Favorita");

        List<LocationGroup> alertList = Arrays.asList(locationGroup1);

        given(repository.findAll()).willReturn(alertList);

        mvc.perform(get("/locationGroup")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(locationGroup1.getName())));
    }

    @Test
    public void update_expected_ok() throws Exception{
        LocationGroup locationGroup = new LocationGroup("FavoritaUpdated");

        List<LocationGroup> locationGroups = Arrays.asList(locationGroup);

        given(repository.findAll()).willReturn(locationGroups);

        JSONObject json = new JSONObject();
        json.put("name", "FavoritaUpdated2");

        mvc.perform(post("/locationGroup/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("FavoritaUpdated2")));

    }

    @Test
    public void delete_Expected_ok() throws Exception{
        mvc.perform(delete("locationGroup/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
