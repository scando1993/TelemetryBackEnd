
package net.pacificsoft.microservices.favorita;

import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.models.application.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
@SpringBootApplication
//@EnableDiscoveryClient

public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);
    @Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {

		return new RepositoryRestConfigurerAdapter() {

			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				config.setRepositoryDetectionStrategy(
						RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
                                config.exposeIdsFor(Alerta.class, Device.class, Features.class,
                                                    GoApiResponse.class, Group.class, LocationGroup.class, LocationNames.class,
                                                    LocationPriority.class, Mac.class, Message.class, MessageGuess.class,
                                                    Prediction.class, Probabilities.class, RawSensorData.class, SigfoxMessage.class,
                                                    Status.class, Telemetria.class, Tracking.class, WifiScan.class, Bodega.class,
                                                    Ciudad.class, Formato.class, Furgon.class, Locales.class, Producto.class,
                                                    Provincia.class, Ruta.class, Zona.class);
                                config.setDefaultPageSize(Integer.MAX_VALUE);
                                config.setReturnBodyOnCreate(Boolean.TRUE);
                                config.setReturnBodyOnUpdate(Boolean.TRUE);
                                config.setReturnBodyForPutAndPost(Boolean.TRUE);
			}
		};
	}
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}