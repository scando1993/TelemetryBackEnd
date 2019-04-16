package net.pacificsoft.microservices.favorita.repository;

import java.util.List;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface StatusRepository extends JpaRepository<Status, Long>{
    List<Status> findByDeviceOrderByLastTransmisionDesc(Device device);
}
