package net.pacificsoft.microservices.favorita;

import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.models.Formato;
import net.pacificsoft.microservices.favorita.models.Locales;
import net.pacificsoft.microservices.favorita.models.Ubicacion;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UbicacionTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port + "/api";
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void testGetAllUsers() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/ubicacion",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetUserById() {
		Ubicacion ubicacion = restTemplate.getForObject(getRootUrl() + "/ubicacion/1", Ubicacion.class);
		System.out.println(ubicacion.getZone());
		assertNotNull(ubicacion);
	}
//
	@Test
	public void testCreateUser() {
		Ubicacion ubicacion = new Ubicacion();
		ubicacion.setCity("ciudad");
		ubicacion.setProvince("provincia");
		ubicacion.setRegional("rgional");
                Bodega bodega = new Bodega();
                Locales locales = new Locales();
                Formato formato = new Formato();
                ubicacion.setBodegas(null);
                ubicacion.setLocales(null);
                ubicacion.setFormatos(null);

		ResponseEntity<Ubicacion> postResponse = restTemplate.postForEntity(getRootUrl() + "/Ubicacion", ubicacion, Ubicacion.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

//	@Test
//	public void testUpdatePost() {
//		int id = 1;
//		User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
//		user.setFirstName("admin1");
//		user.setLastName("admin2");
//
//		restTemplate.put(getRootUrl() + "/users/" + id, user);
//
//		User updatedUser = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
//		assertNotNull(updatedUser);
//	}
//
//	@Test
//	public void testDeletePost() {
//		int id = 2;
//		User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
//		assertNotNull(user);
//
//		restTemplate.delete(getRootUrl() + "/users/" + id);
//
//		try {
//			user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
//		} catch (final HttpClientErrorException e) {
//			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
//		}
//	}

}
