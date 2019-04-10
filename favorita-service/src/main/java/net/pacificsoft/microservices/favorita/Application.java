
package net.pacificsoft.microservices.favorita;

import java.sql.Types;
import java.util.Date;
import javax.sql.DataSource;
import net.pacificsoft.microservices.favorita.models.*;
import net.pacificsoft.microservices.favorita.models.application.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.mapping.RepositoryDetectionStrategy;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
@SpringBootApplication
//@EnableIgniteRepositories
//@EnableDiscoveryClient

public class Application {
    //asdm
    //@Autowired
    //DataSource datasource;
    private static final Logger logger = LogManager.getLogger(Application.class);
    //private static final ThreadStartRuta ts = new ThreadStartRuta();
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //ts.start();
    }
    
    
    
    @Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {

		return new RepositoryRestConfigurerAdapter() {

			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				config.setRepositoryDetectionStrategy(
						RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
                                config.exposeIdsFor(Alerta.class, Device.class, Family.class, Features.class,
                                                    GoApiResponse.class, Group.class, LocationGroup.class, LocationNames.class,
                                                    LocationPriority.class, Mac.class, Message.class, MessageGuess.class,
                                                    Prediction.class, Probabilities.class, RawSensorData.class, SigfoxMessage.class,
                                                    Status.class, Telemetria.class, Tracking.class, WifiScan.class, Bodega.class,
                                                    Ciudad.class, Formato.class, Furgon.class, Locales.class, Producto.class,
                                                    Provincia.class, Ruta.class, Zona.class, ConfigurationDevice.class);
                                config.setDefaultPageSize(Integer.MAX_VALUE);
                                config.setReturnBodyOnCreate(Boolean.TRUE);
                                config.setReturnBodyOnUpdate(Boolean.TRUE);
                                config.setReturnBodyForPutAndPost(Boolean.TRUE);
			}
		};
	}
        
        
      /*  	@SuppressWarnings("deprecation")
	@Bean
	public Ignite igniteInstance() {
		IgniteConfiguration cfg = new IgniteConfiguration();
		cfg.setIgniteInstanceName("ignite-1");
		cfg.setPeerClassLoadingEnabled(true);
		CacheConfiguration<Long, Mac> ccfg2 = new CacheConfiguration<>("MacCache");
		ccfg2.setIndexedTypes(Long.class, Mac.class);
		ccfg2.setWriteBehindEnabled(true);
		ccfg2.setReadThrough(true);
		ccfg2.setWriteThrough(true);
		CacheJdbcPojoStoreFactory<Long, Mac> f2 = new CacheJdbcPojoStoreFactory<>();
		f2.setDataSource(datasource);
		f2.setDialect(new MySQLDialect());
		JdbcType jdbcContactType = new JdbcType();
		jdbcContactType.setCacheName("MacCache");
		jdbcContactType.setKeyType(Long.class);
		jdbcContactType.setValueType(Mac.class);
		jdbcContactType.setDatabaseTable("mac");
		jdbcContactType.setDatabaseSchema("ColdChainTracking");
		//jdbcContactType.setKeyFields(new JdbcTypeField(Types.INTEGER, "id", Long.class, "id"));
		//jdbcContactType.setValueFields(new JdbcTypeField(Types.VARCHAR, "mac", String.class, "mac"),
		//		new JdbcTypeField(Types.VARCHAR, "family", String.class, "family"));
		f2.setTypes(jdbcContactType);
		ccfg2.setCacheStoreFactory(f2);
                
		cfg.setCacheConfiguration(ccfg2);
		return Ignition.start(cfg);
	}
	
	@Bean
	public MetricsEndpointMetricReader metricsEndpointMetricReader(final MetricsEndpoint metricsEndpoint) {
		return new MetricsEndpointMetricReader(metricsEndpoint);
	}*/
    
}