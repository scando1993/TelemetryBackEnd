import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDiscoveryClient
public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args){
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        EventHolderBean bean = ctx.getBean(EventHolderBean.class);
        logger.debug("Loka Core Fetching: " + ( bean.getEventFired() ? "true":"false" ));
    }
}
