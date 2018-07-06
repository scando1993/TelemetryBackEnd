package net.pacificsoft.microservices.telemetry.sms;

import net.pacificsoft.microservices.telemetry.sms.events.EventHolderBean;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args){
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        EventHolderBean bean = ctx.getBean(EventHolderBean.class);
        logger.debug("SMS Fetching: " + ( bean.getEventFired() ? "true":"false" ));
    }
}
