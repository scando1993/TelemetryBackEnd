package net.pacificsoft.microservices.loka.app;

import net.pacificsoft.microservices.loka.app.model.Login;
import net.pacificsoft.microservices.loka.app.modelProto.LokaAppProto;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@ComponentScan
public class LokaAppTest {
    protected Logger logger = Logger.getLogger(LokaAppTest.class);
    private String notValidToken = "eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZUUFdcL1RRQmkraEZSRnFoU1NpbFlnVkJiS2hod0p4aTdrd3lWR1RoelpUaEZGSXJyWVYzT3A3VFBuYzVvc0tCTU1IVm9CbFpENEF3ejlKN0R3QXhBTXJKMVp1WE0rYkRkSUZUZWQzdWU1NTNtXC83dndDcklRVVBISW94RzRvQlc3a1lGOEtBNHA5SjBSV1JERWJTMUdJcUkxWXpIZ1NFN3M4QXFZbmx3YzVGZVN4emNDNk9vQkRXSEdoNzFTMFwvZ0JaYkdkRXdVTkNuWm5pQVlVZU9pTDBVRnBvVzRTaWpFRWluVHZOZzlWOVVJYVdSU0tmdFlrdmp3Sk1rYjBQU2tsTUpkYWhDRzFZSEVFK3c5QU4wOVJWNU1PK2kyd1ZyTUdJdlNMY0ZhT1FnUnZUWkNPRzNZcUIySTRLcmdjd0RIbDJseW94bUVoZDRDSk5uMWZ3R3J3QmhWR1E0NGYzN3I2Z1NrSkhxaFBYNVZWajRvZmJYZDhqTmo3QXdwenJUN2JlZnpcLzVQT25tQWVBOWVYRDFteVIrdXdZbVgxXC8rdVJzM09tY3hzSmxLUGFIdGpBS2VUVGxSTmlrU3pqOCtkVDZjWGJ4N2NZMDdDOGJ1XC84OWp1enJyM0xoT3ZBQlN5RWhxUmx6MnFNRHZaUzVldTFwOFBvV3haR0F2Y0JIZktKOGhlMkdSQ1BOeUM1UzQ4Mzd6bWVtYUt2ZXFqWmJTN3NrTnhRd0ZVc3pHVmNWWXhPK2s0aDFaYnltR29XaHRJOFBaU0hIRTFjZ29MNlBwdDZVcDJsRjZEWGxQcWN2R0hDakdRS05xTkd0YVZXOWsrVk51eGlZRHBCM1dZMEJ1bTRyNW5PT3FiTXJaTnpOb1dXd0dwTVZ1cFFGZGJtbDdjcVwvVDFFd3RTMmhycHJLcjFLc203OVVsejgxbFF0cjVIXC9CeXUzUzVvK2xtejJocXo4Uk1iNkpCUktGTmhvOUQ1RG1VUklIa1FyNUp4WGlSeFErVVZNTFwvM1wvSHYwMjhuOTM3eXJYZ0tWb2JRalJEXC9SNldFMUk2OFBxSnZ6OCsyMWo3K09vNjNjclpKWFwvNENoYjZhUitVRUFBQT0iLCJzdWIiOiJlanVyYWRvdkBzZW1ncm91cC5sYSIsInJvbGVzIjpbIlJPTEVfQURNSU5fRURJVCIsIlJPTEVfQURNSU5fTElTVCIsIlJPTEVfQURNSU5fUEVSTUlTU0lPTlNfTElTVCIsIlJPTEVfQURNSU5fUk9MRVNfRURJVCIsIlJPTEVfQURNSU5fUk9MRVNfTElTVCIsIlJPTEVfQVBJX0RFVklDRVMiLCJST0xFX0RBU0hCT0FSRCIsIlJPTEVfREVWSUNFX0VESVQiLCJST0xFX0RFVklDRV9MSVNUIiwiUk9MRV9FTlRJVFlfREVMRVRFIiwiUk9MRV9FTlRJVFlfRURJVCIsIlJPTEVfRU5USVRZX0xJU1QiLCJST0xFX0VOVElUWV9SRU1PVkVfUEhPVE8iLCJST0xFX05PVElGSUNBVElPTl9ERUxFVEUiLCJST0xFX05PVElGSUNBVElPTl9FRElUIiwiUk9MRV9OT1RJRklDQVRJT05fTElTVCIsIlJPTEVfUkVQT1JUX1NIT1ciXSwiZXhwIjoxNTIwODc0ODAzLCJpYXQiOjE1MjA4NzEyMDN9.Q3pnhtXU_-NMDXdqMyyf3ePiImY1819kIWYHl9cx3QY";

    @Autowired
    TestRestTemplate template;

    @Test
    public void testLogin() {
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokaloka")
                .build();
        LokaAppProto.Account a = this.template.postForObject("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);
        logger.info("Account: " + a);
        Assert.assertEquals(a.getUsername(), "ejuradov@semgroup.la");
    }

    @Test
    public void testFailedLogin(){
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokalok")
                .build();
        ResponseEntity<LokaAppProto.Account> a = this.template.
                postForEntity("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);

        logger.info("Account: " + a);
        Assert.assertEquals(HttpStatus.UNAUTHORIZED, a.getStatusCode());
    }

    @Test
    public void testLoginIncorrectMethod(){
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokaloka")
                .build();
        ResponseEntity<LokaAppProto.Account> get = this.template.
                exchange("/login",
                        HttpMethod.GET,
                        new HttpEntity<>(loginBin),
                        LokaAppProto.Account.class);
        Assert.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, get.getStatusCode());

        ResponseEntity<LokaAppProto.Account> put = this.template.
                exchange("/login",
                        HttpMethod.PUT,
                        new HttpEntity<>(loginBin),
                        LokaAppProto.Account.class);
        Assert.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, put.getStatusCode());

        ResponseEntity<LokaAppProto.Account> patch = this.template.
                exchange("/login",
                        HttpMethod.PATCH,
                        new HttpEntity<>(loginBin),
                        LokaAppProto.Account.class);
        Assert.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, patch.getStatusCode());

        ResponseEntity<LokaAppProto.Account> delete = this.template.
                exchange("/login",
                        HttpMethod.DELETE,
                        new HttpEntity<>(loginBin),
                        LokaAppProto.Account.class);
        Assert.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, delete.getStatusCode());
    }

    @Test
    public void testDeviceList(){
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokaloka")
                .build();
        LokaAppProto.Account a = this.template.postForObject("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + a.getAccessToken());

        ResponseEntity<LokaAppProto.Devices> devices = this.template.exchange(
                "/deviceList",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaAppProto.Devices.class);
        logger.info("Response: " + devices.toString());
        Assert.assertNotNull(devices.getBody());
    }

    @Test
    public void testDeviceListNotAuthorized(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + notValidToken);

        ResponseEntity<LokaAppProto.Devices> devices = this.template.exchange(
                "/deviceList",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaAppProto.Devices.class);
        logger.info("Response: " + devices.toString());
        Assert.assertEquals(devices.getStatusCode(), HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void testDeviceListNoAuthenticationHeader(){
        ResponseEntity<LokaAppProto.Devices> devices = this.template.exchange(
                "/deviceList",
                HttpMethod.GET,
                new HttpEntity<>(null),
                LokaAppProto.Devices.class);
        logger.info("Response: " + devices.toString());
        Assert.assertEquals(devices.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    public void testDeviceListIncorrectMethod(){
        Login login = new Login("ejuradov@semgroup.la","lokaloka");
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername(login.getUsername())
                .setPassword(login.getPassword())
                .build();
        LokaAppProto.Account a = this.template.postForObject("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + a.getAccessToken());

        ResponseEntity<LokaAppProto.Devices> post = this.template.exchange(
                "/deviceList",
                HttpMethod.POST,
                new HttpEntity<>(headers),
                LokaAppProto.Devices.class);
        Assert.assertEquals(post.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);

        ResponseEntity<LokaAppProto.Devices> put = this.template.exchange(
                "/deviceList",
                HttpMethod.PUT,
                new HttpEntity<>(headers),
                LokaAppProto.Devices.class);
        Assert.assertEquals(put.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);

        ResponseEntity<LokaAppProto.Devices> patch = this.template.exchange(
                "/deviceList",
                HttpMethod.PATCH,
                new HttpEntity<>(headers),
                LokaAppProto.Devices.class);
        Assert.assertEquals(patch.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);

        ResponseEntity<LokaAppProto.Devices> delete = this.template.exchange(
                "/deviceList",
                HttpMethod.DELETE,
                new HttpEntity<>(headers),
                LokaAppProto.Devices.class);
        Assert.assertEquals(delete.getStatusCode(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Test
    public void testDeviceDetailsGetMethod(){
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokaloka")
                .build();

        LokaAppProto.Account a = this.template.postForObject("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + a.getAccessToken());

        Map<String,String> params = new HashMap<>();
        params.put("deviceId", "5042045");
        params.put("startDate", "1517524216");
        params.put("endDate", "1519338616");

        ResponseEntity<LokaAppProto.Device> device = this.template.exchange(
                "/deviceDetails?deviceId={deviceId}&startDate={startDate}&endDate={endDate}",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaAppProto.Device.class,
                params
        );
        Assert.assertEquals(device.getBody().getId(), 5042045);
    }

    @Test
    public void testDeviceDetailsPostMethod(){
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokaloka")
                .build();

        LokaAppProto.Account a = this.template.postForObject("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + a.getAccessToken());

        LokaAppProto.DeviceParams params = LokaAppProto.DeviceParams
                .newBuilder()
                .setDeviceId(5042045)
                .setStartDate(1517524216)
                .setEndDate(1519338616)
                .build();

        ResponseEntity<LokaAppProto.Device> device = this.template.exchange(
                "/deviceDetails",
                HttpMethod.POST,
                new HttpEntity<>(params, headers),
                LokaAppProto.Device.class
        );

        Assert.assertEquals(device.getBody().getId(), 5042045);
    }

    @Test
    public void testDeviceDetailsRequiredParams(){
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokaloka")
                .build();

        LokaAppProto.Account a = this.template.postForObject("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + a.getAccessToken());

        LokaAppProto.DeviceParams params = LokaAppProto.DeviceParams
                .newBuilder()
                .setDeviceId(5042045)
                .build();

        ResponseEntity<LokaAppProto.Device> device = this.template.exchange(
                "/deviceDetails",
                HttpMethod.POST,
                new HttpEntity<>(params, headers),
                LokaAppProto.Device.class
        );

        Assert.assertEquals(device.getBody().getId(), 5042045);

        ResponseEntity<LokaAppProto.Device> deviceGet = this.template.exchange(
                "/deviceDetails?deviceId=5042045",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaAppProto.Device.class
        );
        Assert.assertEquals(deviceGet.getBody().getId(),5042045 );

    }

    @Test
    public void testDeviceDetailsOptionalParams(){
        LokaAppProto.Login loginBin = LokaAppProto.Login
                .newBuilder()
                .setUsername("ejuradov@semgroup.la")
                .setPassword("lokaloka")
                .build();

        LokaAppProto.Account a = this.template.postForObject("/login", new HttpEntity<>(loginBin), LokaAppProto.Account.class);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + a.getAccessToken());

        LokaAppProto.DeviceParams params1 = LokaAppProto.DeviceParams
                .newBuilder()
                .setDeviceId(5042045)
                .setStartDate(1517524216)
                .build();

        ResponseEntity<LokaAppProto.Device> device1 = this.template.exchange(
                "/deviceDetails",
                HttpMethod.POST,
                new HttpEntity<>(params1, headers),
                LokaAppProto.Device.class
        );

        Assert.assertEquals(device1.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<LokaAppProto.Device> device2 = this.template.exchange(
                "/deviceDetails?deviceId=5042045&startDate=1517524216",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaAppProto.Device.class
        );
        Assert.assertEquals(device2.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        LokaAppProto.DeviceParams params3 = LokaAppProto.DeviceParams
                .newBuilder()
                .setDeviceId(5042045)
                .setEndDate(1519338616)
                .build();

        ResponseEntity<LokaAppProto.Device> device3 = this.template.exchange(
                "/deviceDetails",
                HttpMethod.POST,
                new HttpEntity<>(params3, headers),
                LokaAppProto.Device.class
        );

        Assert.assertEquals(device3.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

        ResponseEntity<LokaAppProto.Device> device4 = this.template.exchange(
                "/deviceDetails?deviceId=5042045&endDate=1519338616",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                LokaAppProto.Device.class
        );
        Assert.assertEquals(device4.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @TestConfiguration
    static class Config{
        @Bean
        public RestTemplateBuilder restTemplateBuilder(){
            return new RestTemplateBuilder().additionalMessageConverters(new ProtobufHttpMessageConverter());
        }
    }
}
