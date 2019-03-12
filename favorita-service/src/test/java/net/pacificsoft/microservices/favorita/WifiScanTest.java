package net.pacificsoft.microservices.favorita;

import net.pacificsoft.microservices.favorita.models.Bodega;
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
public class WifiScanTest {

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

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/bodega",
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetUserById() {
		Bodega bodega = restTemplate.getForObject(getRootUrl() + "/bodega/", Bodega.class);
		System.out.println(bodega.getName());
		assertNotNull(bodega);
	}
//
//	@Test
//	public void testCreateUser() {
//		User user = new User();
//		user.setEmailId("admin@gmail.com");
//		user.setFirstName("admin");
//		user.setLastName("admin");
//		user.setCreatedBy("admin");
//		user.setUpdatedby("admin");
//
//		ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);
//		assertNotNull(postResponse);
//		assertNotNull(postResponse.getBody());
//	}
//
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
