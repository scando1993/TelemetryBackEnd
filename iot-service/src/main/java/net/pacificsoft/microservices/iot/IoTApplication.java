package net.pacificsoft.microservices.iot;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("net.pacificsoft.microservices.iot")
public class IoTApplication {

    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }

}
