package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;
import net.pacificsoft.microservices.favorita.models.DetailConfiguration;
import org.apache.ignite.springdata.repository.IgniteRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface DetailConfigurationRepository extends JpaRepository<DetailConfiguration, Long>{
    
}
