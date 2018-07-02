package net.pacificsoft.microservices.loka.core;

import net.pacificsoft.microservices.loka.core.config.RestTestConfig;
import net.pacificsoft.microservices.loka.core.modelProto.LokaCoreProto;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.*;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static com.sun.org.apache.xerces.internal.util.PropertyState.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.AnyOf.anyOf;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RestTestConfig.class)
public class LokaCoreTest {

    protected Logger logger = Logger.getLogger(LokaCoreTest.class);
    private String notValidToken = "d4703b4f-b4a7-4976-89f8-0ce0ceba5c51";
    private String validToken = "d4703b4f-b4a7-4976-89f8-0ce0ceba5c50";
    private long validDevice = 5042045;
    private long invalidDevice = 10203434;

    @Autowired
    TestRestTemplate template;

    @Test
    public void testLogin(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);
        ResponseEntity<LokaCoreProto.ControlMessage> request = template.exchange("/login",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaCoreProto.ControlMessage.class);
        Assert.assertEquals(request.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testNotValidLogin(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", notValidToken);
        ResponseEntity<?> request = template.exchange("/login",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaCoreProto.ControlMessage.class);
        Assert.assertEquals(request.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testSubscribeTerminal(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);
        ResponseEntity<LokaCoreProto.ControlMessage> request = template.exchange("/subscribe_terminal/" + validDevice,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaCoreProto.ControlMessage.class);
        Assert.assertEquals(request.getStatusCode(), HttpStatus.OK);
        Assert.assertThat(request.getBody().getMessage(),
                anyOf(containsString("ALREADY SUBSCRIBED"), containsString("SUBSCRIBED")));
        Assert.assertEquals(request.getBody().getStatus(), 200);
    }

    @Test
    public void testUnsubscribeTerminal(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);
        ResponseEntity<LokaCoreProto.ControlMessage> request = template.exchange("/unsubscribe_terminal/" + validDevice,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaCoreProto.ControlMessage.class);
        Assert.assertEquals(request.getStatusCode(), HttpStatus.OK);
        Assert.assertThat(request.getBody().getMessage(),
                anyOf(containsString("NOT SUBSCRIBED"), containsString("UNSUBSCRIBED")));
        Assert.assertEquals(request.getBody().getStatus(), 200);
    }

    @Test
    public void testNotValidSubscription(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);
        ResponseEntity<LokaCoreProto.ControlMessage> request = template.exchange("/subscribe_terminal/" + invalidDevice,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaCoreProto.ControlMessage.class);
        Assert.assertEquals(request.getStatusCode(), HttpStatus.FORBIDDEN);
        Assert.assertEquals(request.getBody().getMessage(), "NO_PERMISSION");
        Assert.assertEquals(request.getBody().getStatus(), 403);
    }

    @Test
    public void testNotValidUnsubscription(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);
        ResponseEntity<LokaCoreProto.ControlMessage> request2 = template.exchange("/unsubscribe_terminal/" + invalidDevice,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaCoreProto.ControlMessage.class);
        Assert.assertEquals(request2.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(request2.getBody().getMessage(), "NOT SUBSCRIBED");
        Assert.assertEquals(request2.getBody().getStatus(), 200);
    }

    @Test
    public void testAllowedSubcriptions(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", validToken);
        ResponseEntity<LokaCoreProto.Subscriptions> request2 = template.exchange("/subscription/allowed/",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaCoreProto.Subscriptions.class);
        Assert.assertEquals(request2.getStatusCode(), HttpStatus.OK);
        Assert.assertThat(request2.getBody().getIdList().toString(), containsString(Long.toString(validDevice)));
    }

//    @TestConfiguration
//    static class Config{
//        @Bean
//        public RestTemplateBuilder restTemplateBuilder(){
//            return new RestTemplateBuilder().additionalMessageConverters(new ProtobufHttpMessageConverter());
//        }
//    }
}
