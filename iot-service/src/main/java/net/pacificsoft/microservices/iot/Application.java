package net.pacificsoft.microservices.iot;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration

//@EnableEurekaClient

public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

    }
}
