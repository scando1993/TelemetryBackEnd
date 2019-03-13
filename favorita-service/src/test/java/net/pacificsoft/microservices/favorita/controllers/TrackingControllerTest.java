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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackingController.class)
public class TrackingControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TrackingRepository repository;
    @Autowired
    private DeviceRepository drepository;


    @Test
    public void added_expected_ok() throws Exception{
        long deviceID = 1;
        long locationGroupID = 1;
        String dtm = "2019-09-01T14:01";
        String location = "location1";

        JSONObject json = new JSONObject();
        json.put("deviceID", deviceID);
        json.put("locationGroupID", locationGroupID);
        json.put("dtm", dtm);
        json.put("location", location);

        mvc.perform(post("/tracking/{deviceid}/{locationid}", deviceID, locationGroupID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.deviceID", is(deviceID)))
                .andExpect(jsonPath("$.locationGroupID", is(locationGroupID)))
                .andExpect(jsonPath("$.location", is(location)))
                .andExpect(jsonPath("$.dtm", is(dtm)));
    }

    @Test

    public void givenLocationGroup_whenGetLocationGroup_thenReturnJSONArray() throws Exception{
        long trackingID = 1;
        long deviceID = 1;
        long locationGroupID = 1;
        String dtm = "2019-09-01T14:01";
        String location = "location1";

        JSONObject json = new JSONObject();
        json.put("deviceID", deviceID);
        json.put("locationGroupID", locationGroupID);
        json.put("dtm", dtm);
        json.put("location", location);


        mvc.perform(get("/tracking")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(trackingID)))
                .andExpect(jsonPath("$[0].deviceID", is(deviceID)))
                .andExpect(jsonPath("$[0].locationGroupID", is(locationGroupID)))
                .andExpect(jsonPath("$[0].location", is(location)))
                .andExpect(jsonPath("$[0].dtm", is(dtm)));
    }

    @Test
    public void update_expected_ok() throws Exception{
        long trackiingID = 1;
        long deviceID = 1;
        long locationGroupID = 1;
        String dtm = "2019-09-01T14:01";
        String location = "location1";

        JSONObject json = new JSONObject();
        json.put("deviceID", deviceID);
        json.put("locationGroupID", locationGroupID);
        json.put("dtm", dtm);
        json.put("location", location);

        mvc.perform(post("/locationGroup/{id}/{deviceid}/{locationid}", trackiingID,deviceID, locationGroupID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void delete_Expected_ok() throws Exception{
        long trackingID = 1;
        mvc.perform(delete("locationGroup/{id}", trackingID)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
