package net.pacificsoft.microservices.loka.core.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;

@TestConfiguration
public class RestTestConfig {
    @Bean
    public RestTemplateBuilder restTemplateBuilder(){
        return new RestTemplateBuilder().additionalMessageConverters(new ProtobufHttpMessageConverter());
    }
}
