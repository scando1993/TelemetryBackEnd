package net.pacificsoft.microservices.favorita;

//import org.springframework.cloud.sleuth.*;
//import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("net.pacificsoft.microservices.favorita")
@EntityScan
@EnableJpaAuditing
@EnableJpaRepositories
@EnableTransactionManagement
@IntegrationComponentScan
@EnableCaching
public class FavoritaApplication {
//    @Bean
//    public AlwaysSam defaultSampler(){
//        return new ProbabilityBasedSampler(null);
//    }

}
