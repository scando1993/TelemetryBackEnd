package net.pacificsoft.microservices.loka.core.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googlecode.protobuf.format.JsonFormat;
import net.pacificsoft.microservices.loka.core.modelProto.LokaCoreProto;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Type;
import java.util.List;

import static net.pacificsoft.microservices.loka.core.api.LokaCoreApi.*;
import static net.pacificsoft.microservices.loka.core.util.SSLTrustManager.acceptAll;
import static net.pacificsoft.microservices.loka.core.util.SSLTrustManager.trustSelfSignedSSL;

@RestController
public class Api {
    protected static Logger logger = LogManager.getLogger(Api.class);
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping(path = "/login", produces = "application/x-protobuf")
    public ResponseEntity<LokaCoreProto.ControlMessage> login(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loka_core_key);
        trustSelfSignedSSL();
        acceptAll();
        ResponseEntity<String> request;
        try {
            request = restTemplate.exchange(loka_core_subscription_list,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
            logger.debug("Request received: " + request.toString());
        }catch (HttpStatusCodeException e){
            logger.error(e.toString());
            return new ResponseEntity<>(LokaCoreProto.ControlMessage.newBuilder().build(), e.getStatusCode());
        }

        String devicesJson = request.getBody();

        Type type = new TypeToken<List<Long>>() {}.getType();
        List<Long> devices = new Gson().fromJson(devicesJson, type);

        LokaCoreProto.ControlMessage controlMessage = LokaCoreProto
                .ControlMessage
                .newBuilder()
                .setMessage(devices.toString())
                .setStatus(HttpStatus.OK.value())
                .build();
        ResponseEntity<LokaCoreProto.ControlMessage> responseEntity = new ResponseEntity<>(controlMessage,HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping(path = "/subscribe_terminal/{id}", produces = "application/x-protobuf")
    public ResponseEntity<LokaCoreProto.ControlMessage> subscribe(
            @PathVariable("id") long id){
        logger.info("Subscribe loka device id: " + id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loka_core_key);
        trustSelfSignedSSL();
        acceptAll();
        ResponseEntity<String> request;
        try {
            request = restTemplate.exchange(loka_core_subscription + id,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
            logger.debug("Request received: " + request.toString());
        }catch (HttpStatusCodeException e){
            logger.debug("Status code: " + e.getStatusText());
            logger.error(e.toString());
            String json = e.getResponseBodyAsString();
            JsonFormat jsonFormat = new JsonFormat();
            LokaCoreProto.ControlMessage.Builder builder = LokaCoreProto.ControlMessage.newBuilder();
            LokaCoreProto.ControlMessage controlMessage;
            try {
                jsonFormat.merge(new ByteArrayInputStream(json.getBytes()), builder);
                controlMessage = builder.build();
            }catch (Exception ex){
                return new ResponseEntity<>(LokaCoreProto.ControlMessage.newBuilder().build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(controlMessage, e.getStatusCode());
        }

        String json = request.getBody();
        JsonFormat jsonFormat = new JsonFormat();
        LokaCoreProto.ControlMessage.Builder builder = LokaCoreProto.ControlMessage.newBuilder();
        LokaCoreProto.ControlMessage controlMessage;
        try {
            jsonFormat.merge(new ByteArrayInputStream(json.getBytes()), builder);
            controlMessage = builder.build();
        }catch (Exception ex){
            return new ResponseEntity<>(LokaCoreProto.ControlMessage.newBuilder().build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(controlMessage, HttpStatus.OK);
    }

    @GetMapping(path = "/unsubscribe_terminal/{id}", produces = "application/x-protobuf")
    public ResponseEntity<?> unsubscribe(@PathVariable("id") long id){
        logger.info("Unsubscribe loka device id: " + id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loka_core_key);
        trustSelfSignedSSL();
        acceptAll();
        ResponseEntity<String> request;
        try {
            request = restTemplate.exchange(loka_core_unsubscribe + id,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
            logger.info("Request received: " + request.toString());
        }catch (HttpStatusCodeException exp){
            logger.error(exp.toString());
            String json = exp.getResponseBodyAsString();
            JsonFormat jsonFormat = new JsonFormat();
            LokaCoreProto.ControlMessage.Builder newBuilder = LokaCoreProto.ControlMessage.newBuilder();
            LokaCoreProto.ControlMessage controlMessage;
            try {
                jsonFormat.merge(new ByteArrayInputStream(json.getBytes()), newBuilder);
                controlMessage = newBuilder.build();
            }catch (Exception ex){
                return new ResponseEntity<>(LokaCoreProto.ControlMessage.newBuilder().build(),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>(controlMessage, exp.getStatusCode());
        }

        String json = request.getBody();
        JsonFormat jsonFormat = new JsonFormat();
        LokaCoreProto.ControlMessage.Builder builder = LokaCoreProto.ControlMessage.newBuilder();
        LokaCoreProto.ControlMessage controlMessage;
        try {
            jsonFormat.merge(new ByteArrayInputStream(json.getBytes()), builder);
            controlMessage = builder.build();
        }catch (Exception ex){
            return new ResponseEntity<>(LokaCoreProto.ControlMessage.newBuilder().build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(controlMessage,HttpStatus.OK);
    }

    @GetMapping(path = "/subscription/allowed/", produces = "application/x-protobuf")
    public ResponseEntity<LokaCoreProto.Subscriptions> allowed_subscription(){
        logger.info("Allowed subscription devices");
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + loka_core_key);
        trustSelfSignedSSL();
        acceptAll();
        ResponseEntity<String> request;
        try {
            request = restTemplate.exchange(loka_core_subscription_allowed,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );
            logger.info("Request received: " + request.toString());
        }catch (HttpStatusCodeException e){
            logger.error(e.toString());
            return new ResponseEntity<>(LokaCoreProto.Subscriptions.newBuilder().build(), e.getStatusCode());
        }

        String devicesJson = "{" + "id:" + request.getBody() + "}";

        JsonFormat jsonFormat = new JsonFormat();
        LokaCoreProto.Subscriptions.Builder newBuilder = LokaCoreProto.Subscriptions.newBuilder();
        LokaCoreProto.Subscriptions controlMessage;
        try {
            jsonFormat.merge(new ByteArrayInputStream(devicesJson.getBytes()), newBuilder);
            controlMessage = newBuilder.build();
        }catch (Exception ex){
            return new ResponseEntity<>(LokaCoreProto.Subscriptions.newBuilder().build(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<LokaCoreProto.Subscriptions> responseEntity = new ResponseEntity<>(controlMessage,HttpStatus.OK);
        return responseEntity;
    }
}
