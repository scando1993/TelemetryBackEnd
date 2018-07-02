package net.pacificsoft.microservices.loka.app.api;


import com.google.protobuf.Descriptors;
import com.googlecode.protobuf.format.JsonFormat;
import net.pacificsoft.microservices.loka.app.model.Login;
import net.pacificsoft.microservices.loka.app.modelProto.LokaAppProto;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.util.Map;

@RestController()
public class Api {

    protected static Logger logger = LoggerFactory.getLogger(Api.class.getName());

    @PostMapping(value = "/login",produces = "application/x-protobuf",consumes = "application/x-protobuf")
    public ResponseEntity<LokaAppProto.Account> login(@RequestBody LokaAppProto.Login login){
        ResponseEntity<LokaAppProto.Account> response;
        logger.info(String.format("Loka.login(%s,%s)", login.getUsername(), login.getPassword()));
        Login rest_login = new Login(login.getUsername(),login.getPassword());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> apiResponse;
        try{
            apiResponse = restTemplate.exchange(
                    LokaAppApi.loka_app_login,
                    HttpMethod.POST,
                    new HttpEntity<>(rest_login) ,
                    String.class);
        }catch(HttpClientErrorException e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(LokaAppProto.Account.newBuilder().build(), e.getStatusCode());
        }

        logger.info(String.format("Response received: %s",apiResponse));

        String jsonBody = apiResponse.getBody();
        LokaAppProto.Account.Builder accountBuilder = LokaAppProto.Account.newBuilder();

        JsonFormat jsonFormat = new JsonFormat();

        try {
            jsonFormat.merge(new ByteArrayInputStream(jsonBody.getBytes()), accountBuilder);
            LokaAppProto.Account account = accountBuilder.build();
            logger.info(String.format("AccountProto: %s", account));
            response = new ResponseEntity<>(account, HttpStatus.OK);
        }catch (Exception e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(
                    LokaAppProto.Account.newBuilder().build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

//            Account account = apiResponse.getBody();
//            LokaAppProto.Account accountBinary = LokaAppProto.Account
//                    .newBuilder()
//                    .setUsername(account.getUsername())
//                    .setAccessToken(account.getAccess_token())
//                    .setExpiresIn(account.getExpires_in())
//                    .setRefreshToken(account.getRefresh_token())
//                    .setTokenType(account.getToken_type())
//                    .addAllRoles(account.getRoles())
//                    .build();
//
//            response = new ResponseEntity<>( accountBinary, apiResponse.getStatusCode() );
//
//            logger.info(String.format("Account received: %s", response.toString()));
        return response;
    }

    @GetMapping(value = "/deviceList", produces = "application/x-protobuf")
    public ResponseEntity<LokaAppProto.Devices> listDevices(@RequestHeader(value = "Authorization") String authHeader){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LokaAppProto.Devices> devicesResponseEntity;
        logger.info(String.format("Loka.deviceList(%s)", authHeader));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);

        ResponseEntity<String> response;

        try{
            response = restTemplate.exchange(
                    LokaAppApi.loka_app_list_devices,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class);
        }catch(HttpClientErrorException e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(
                    LokaAppProto.Devices.newBuilder().build(),
                    e.getStatusCode());
        }

        logger.info(String.format("Response received: %s",response));

        logger.info(String.format("Devices received: %s", response.getBody()));

        String jsonBody = "{ \"device\": " + response.getBody() + "}";
        LokaAppProto.Devices.Builder devicesBuilder = LokaAppProto.Devices.newBuilder();

        JsonFormat jsonFormat = new JsonFormat();

        try {
            jsonFormat.merge(new ByteArrayInputStream(jsonBody.getBytes()), devicesBuilder);
            LokaAppProto.Devices devices = devicesBuilder.build();
            logger.info(String.format("DevicesProto: %s", devices));
            devicesResponseEntity = new ResponseEntity<>(devices, HttpStatus.OK);
        }catch (Exception e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(
                    LokaAppProto.Devices.newBuilder().build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return devicesResponseEntity;
    }

    @GetMapping(value = "/deviceDetails",produces = "application/x-protobuf")
    public ResponseEntity<LokaAppProto.Device> deviceDetails(
            @RequestHeader(value = "Authorization") String authHeader,
            @RequestParam MultiValueMap<String, String> params)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LokaAppProto.Device> devicesResponseEntity;
        logger.info(String.format("Loka.deviceDetails(%s)", params));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);

        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(LokaAppApi.loka_app_device_details)
                .queryParams(params);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        logger.info("Url generated: " + builder.build().toString());
        ResponseEntity<String> response;

        try{
            response = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.GET,
                    entity,
                    String.class);

        }catch(HttpClientErrorException e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(
                    LokaAppProto.Device.newBuilder().build(),
                    e.getStatusCode());
        }catch(HttpServerErrorException es){
            logger.error(String.format("Exception at: %s", es));
            return new ResponseEntity<>(
                    LokaAppProto.Device.newBuilder().build(),
                    es.getStatusCode());
        }

        logger.info(String.format("Response received: %s",response));

        logger.info(String.format("Device received: %s", response.getBody()));

        String jsonBody = response.getBody();
        LokaAppProto.Device.Builder deviceBuilder = LokaAppProto.Device.newBuilder();
        JsonFormat jsonFormat = new JsonFormat();

        try {
            jsonFormat.merge(new ByteArrayInputStream(jsonBody.getBytes()), deviceBuilder);
            LokaAppProto.Device device = deviceBuilder.build();
            logger.info(String.format("DeviceProto: %s", device));
            devicesResponseEntity = new ResponseEntity<>(device, HttpStatus.OK);
        }catch (Exception e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(
                    LokaAppProto.Device.newBuilder().build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return devicesResponseEntity;
    }

    @PostMapping(value = "/deviceDetails", produces = "application/x-protobuf", consumes = "application/x-protobuf")
    public ResponseEntity<LokaAppProto.Device> deviceDetails(
            @RequestHeader(value = "Authorization") String authHeader,
            @RequestBody LokaAppProto.DeviceParams params)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LokaAppProto.Device> deviceResponseEntity;
        logger.info(String.format("Loka.deviceDetails(%s)", params));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authHeader);
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(LokaAppApi.loka_app_device_details);
        if (params.getDeviceId() == 0){
            return new ResponseEntity<>(LokaAppProto.Device.newBuilder().build(),HttpStatus.NOT_ACCEPTABLE);
        }else{
            builder.queryParam("deviceId", params.getDeviceId());
        }
        if(params.getStartDate() != 0){
            builder.queryParam("startDate", params.getStartDate());
        }
        if(params.getEndDate() != 0){
            builder.queryParam("endDate", params.getEndDate());
        }
        logger.info("Url generated: " + builder.build().toString());

        ResponseEntity<String> response;

        try{
            response = restTemplate.exchange(
                    builder.build().encode().toUri(),
                    HttpMethod.POST,
                    httpEntity,
                    String.class);

        }catch(HttpClientErrorException e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(
                    LokaAppProto.Device.newBuilder().build(),
                    e.getStatusCode());
        }catch (HttpServerErrorException es){
            logger.error(String.format("Exception at: %s", es));
            return new ResponseEntity<>(
                    LokaAppProto.Device.newBuilder().build(),
                    es.getStatusCode());
        }

        logger.info(String.format("Response received: %s",response));

        logger.info(String.format("Device received: %s", response.getBody()));

//            String jsonBody = "{ \"device\": " + response.getBody() + "}";
        LokaAppProto.Device.Builder deviceBuilder = LokaAppProto.Device.newBuilder();
        JsonFormat jsonFormat = new JsonFormat();

        try {
            jsonFormat.merge(new ByteArrayInputStream(response.getBody().getBytes()), deviceBuilder);
            LokaAppProto.Device device = deviceBuilder.build();
            logger.info(String.format("DeviceProto: %s", device));
            deviceResponseEntity = new ResponseEntity<>(device, HttpStatus.OK);
        }catch (Exception e){
            logger.error(String.format("Exception at: %s", e));
            return new ResponseEntity<>(
                    LokaAppProto.Device.newBuilder().build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return deviceResponseEntity;
    }
}
