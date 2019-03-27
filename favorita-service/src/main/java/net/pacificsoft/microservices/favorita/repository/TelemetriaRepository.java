package net.pacificsoft.microservices.favorita.repository;

import java.util.List;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface TelemetriaRepository extends JpaRepository<Telemetria, Long>{
    List<Telemetria> findByDeviceOrderByDtm(Device device);
}
