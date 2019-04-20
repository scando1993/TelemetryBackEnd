package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import net.pacificsoft.microservices.favorita.models.ConfigurationDevice;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface ConfigurationDeviceRepository extends JpaRepository<ConfigurationDevice, Long>{
    List<ConfigurationDevice> findByDevice(Device device);

}
