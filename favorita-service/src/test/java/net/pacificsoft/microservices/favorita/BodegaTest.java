package net.pacificsoft.microservices.favorita;

import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.models.Ubicacion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BodegaTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port +"/api";
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testGetAllBodega() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/bodega",
                HttpMethod.GET, entity, String.class);

        assertNotNull(response.getBody());
    }

    @Test
    public void testGetBodegaById() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<Bodega> response = restTemplate.exchange(getRootUrl() + "/bodega/1",
                HttpMethod.GET, entity, Bodega.class);

        //Bodega bodega = restTemplate.getForObject(getRootUrl() + "/bodega/1", Bodega.class);
        //System.out.println(bodega.getName());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCreateBodega() {
        Bodega b = new Bodega();
        Ubicacion u = new Ubicacion();
        u.setId(1);
        //u.setBodegas(new HashSet());
        //u.setLocales(new HashSet());
        //u.setFormatos(new HashSet());
        u.setCity("c");
        u.setProvince("p");
        u.setRegional("r");
        u.setZone("z");
        //u.getBodegas().add(b);
        b.setName("asd");
        //b.setUbication(u);
        //ResponseEntity<Ubicacion> postResponse1 = restTemplate.postForEntity(getRootUrl() + "/ubicacion", u, Ubicacion.class);

        restTemplate.put(getRootUrl() + "/bodega/1", b);

        //assertNotNull(postResponse1);
        //assertNotNull(postResponse2);
        Bodega b1 = restTemplate.getForObject(getRootUrl() + "/bodega/1", Bodega.class);
        //System.out.println(postResponse1.getBody());
        System.out.println(b);
        System.out.println(b1);
        //System.out.println(postResponse2.getBody());

        assertEquals(b.getName(), b1.getName());

        //assertNotNull(postResponse2.getBody());

        //assertEquals(re, this);
    }

    @Test
    public void testUpdateBodega() {
        int id = 1;
        Bodega b = restTemplate.getForObject(getRootUrl() + "/bodega/" + id, Bodega.class);

        b.setName("?asd");

        restTemplate.put(getRootUrl() + "/bodega/" + id, b);


        Bodega updatedB = restTemplate.getForObject(getRootUrl() + "/bodega/" + b.getId(), Bodega.class);
        assertNotNull(updatedB);
    }

    @Test
    public void testDeleteBodega() {
        int id = 2;
        Bodega b = restTemplate.getForObject(getRootUrl() + "/bodega/" + id, Bodega.class);
        assertNotNull(b);

        restTemplate.delete(getRootUrl() + "/bodega/" + id);

        try {
            b = restTemplate.getForObject(getRootUrl() + "/bodega/" + id, Bodega.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
