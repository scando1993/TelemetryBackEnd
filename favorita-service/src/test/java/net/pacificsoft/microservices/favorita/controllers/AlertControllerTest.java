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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AlertaController.class)
class AlertaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AlertaRepository repository;

    @Test
    public void givenAlertas_whenGetAlertas_thenReturnJSONArray() throws Exception{
        Alerta alert1 = new Alerta("1", "Message 1");
        Alerta alert2 = new Alerta("2", "Message 2");

        List<Alerta> alertList = Arrays.asList(alert1, alert2);

        given(repository.findAll()).willReturn(alertList);

        mvc.perform(get("/alerts")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type_alert", is(alert1.getType_alert())));
    }

}