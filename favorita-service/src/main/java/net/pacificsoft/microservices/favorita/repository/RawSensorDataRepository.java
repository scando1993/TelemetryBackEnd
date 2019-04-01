package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.RawSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface RawSensorDataRepository extends JpaRepository<RawSensorData, Long>{

    //List<RawSensorData> findAllOrderById ();
}
