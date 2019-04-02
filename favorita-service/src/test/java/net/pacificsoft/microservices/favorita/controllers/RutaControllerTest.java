package net.pacificsoft.microservices.favorita.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import net.pacificsoft.microservices.favorita.controllers.application.RutaController;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(RutaController.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RutaControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    @Autowired
    private RutaRepository repositoryR;

    @Test
    public void givenAlertas_whenGetAlertas_thenReturnJSONArray() throws Exception{
        Ruta r1 = new Ruta(new Date("1999-11-11'T'11:11"), new Date("1999-11-11'T'12:12"));        
        Ruta r2 = new Ruta(new Date("2009-11-11'T'12:12"), new Date("2010-12-12'T'10:10"));
        repositoryR.save(r1);
        repositoryR.save(r2);
        List<Ruta> deviceList = Arrays.asList(r1, r2);

        given(repositoryR.findAll()).willReturn(deviceList);

        mvc.perform(get("/ruta")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].start_date", is(r1.getStart_date())));
    }
    
    /*@Test
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
    }
*/
}