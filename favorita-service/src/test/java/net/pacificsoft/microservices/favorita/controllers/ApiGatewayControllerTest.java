package net.pacificsoft.microservices.favorita.controllers;

import net.pacificsoft.microservices.favorita.Application;
import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.repository.*;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import org.json.JSONObject;
import org.junit.Before;
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

import javax.sql.RowSetMetaData;
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
@WebMvcTest(ApiGatewayController.class)
public class ApiGatewayControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private RawSensorDataController repository;


    @MockBean
    @Autowired
    private DeviceRepository repositoryD;

    private RawSensorData rawSensorData;
    private Device device;
    private SimpleDateFormat dateFormat;
    @Before
    public void init(){
        rawSensorData = new RawSensorData(2L, (float)45.12, new Date(213321), "loquesea");
        device = new Device("test1","2");
        Family family = new Family("test1");
        Group group = new Group("Super");
        group.getFamilies().add(family);
        family.setGroup(group);
        device.setGroup(group);
        rawSensorData.setDevice(device);
        device.getRawSensorDatas().add(rawSensorData);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    }

    @Test
    public void added_expected_ok() throws Exception{

        given(repositoryD.existsById(device.getId())).willReturn(true);
        //given(repositoryLG.existsById(any())).willReturn(true);

        given(repositoryD.findById(any())).willReturn(Optional.of(device));
        //given(repositoryLG.findById(any())).willReturn(Optional.of(new LocationGroup()));


        String formated = dateFormat.format(rawSensorData.getEpochDateTime());

        JSONObject json = new JSONObject();
        json.put("rawData", rawSensorData.getRawData());
        json.put("epoch", rawSensorData.getEpoch());
        json.put("temperature", rawSensorData.getTemperature());
        json.put("epochDateTime", formated);
        json.put("rawData", rawSensorData.getRawData());


        mvc.perform(post("/rawData/{deviceid}", device.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json.toString())
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

}
