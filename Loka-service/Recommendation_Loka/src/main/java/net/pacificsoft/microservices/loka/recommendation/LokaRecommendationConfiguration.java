package net.pacificsoft.microservices.loka.recommendation;

import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("net.pacificsoft.microservices.loka.recommendation")
public class LokaRecommendationConfiguration {
    @Bean
    public AlwaysSampler defaultSampler(){
        return new AlwaysSampler();
    }
}
