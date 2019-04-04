package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.Application;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.LocationGroup;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.*;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private TrackingRepository repository;
    @MockBean
    @Autowired
    private DeviceRepository repositoryD;
    @MockBean
    @Autowired
    private LocationGroupRepository repositoryLG;
    @MockBean
    @Autowired
    private LocationPriorityRepository repositoryLP;
    @MockBean
    @Autowired
    private RutaRepository dsa;


    @Test
    public void create_test() throws Exception{
        Date dsa = new Date(279132123);
        SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String a = as.format(dsa);

        given(repositoryD.existsById(any())).willReturn(true);
        given(repositoryLG.existsById(any())).willReturn(true);

        given(repositoryD.findById(any())).willReturn(Optional.of(new Device()));
        given(repositoryLG.findById(any())).willReturn(Optional.of(new LocationGroup()));

        JSONObject json = new JSONObject();
        json.put("location", "any");
        json.put("dtm", a);


        mvc.perform(post("/tracking/{deviceid}/{locationid}", 1,2)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getAll_test() throws Exception{
        Tracking tracking = new Tracking("Location",new Date(213213));
        Tracking tracking2 = new Tracking("location2",new Date(2222123));
        List<Tracking> locationPriorities = Arrays.asList(tracking,tracking2);

        given(repository.findAll()).willReturn(locationPriorities);

        mvc.perform(get("/tracking")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].location", is(tracking2.getLocation())));
    }
    
    @Test
    public void getById_test() throws Exception{
        Tracking tracking = new Tracking("Location",new Date(213213));

        given(repository.existsById(tracking.getId())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(tracking));

        mvc.perform(get("/tracking/"+tracking.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void update_test() throws Exception{
        Tracking tracking = new Tracking("location", new Date(231321321));

        given(repositoryD.existsById(any())).willReturn(true);
        given(repositoryLG.existsById(any())).willReturn(true);

        given(repositoryD.findById(any())).willReturn(Optional.of(new Device()));
        given(repositoryLG.findById(any())).willReturn(Optional.of(new LocationGroup()));

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(tracking));

        Date dsa = new Date(279132123);
        SimpleDateFormat as = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        String a = as.format(dsa);

        JSONObject json = new JSONObject();
        json.put("location", "any");
        json.put("dtm", a);


        mvc.perform(put("/tracking/{id}/{deviceid}/{locationid}", tracking.getId(), 2,1)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void delete_test() throws Exception{
        Tracking tracking = new Tracking("location", new Date(231321321));
        Device device = new Device("family","device");
        LocationGroup locationGroup = new LocationGroup("locationG");
        tracking.setDevice(device);
        //tracking.setLocationGroup(locationGroup);
        device.getTrackings().add(tracking);
        //locationGroup.getTrackings().add(tracking);

        given(repository.existsById(any())).willReturn(true);
        given(repository.findById(any())).willReturn(Optional.of(tracking));

        mvc.perform(delete("/tracking/{id}", tracking.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

}
