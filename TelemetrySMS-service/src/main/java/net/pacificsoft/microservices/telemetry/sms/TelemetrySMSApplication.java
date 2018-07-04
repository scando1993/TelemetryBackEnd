package net.pacificsoft.microservices.telemetry.sms;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@ComponentScan("net.pacificsoft.microservices.telemetry.sms")
public class TelemetrySMSApplication {
    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }

}
