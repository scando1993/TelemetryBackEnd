package net.pacificsoft.microservices.favorita.repository;

import java.util.Date;
import java.util.List;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface TelemetriaRepository extends JpaRepository<Telemetria, Long>{
    List<Telemetria> findByDeviceOrderByDtmDesc(Device device);
    List<Telemetria> findByDtmLessThanEqualAndDevice(Date end, Device device);
    List<Telemetria> findByDtmBetweenAndDevice(Date start, Date end, Device device);
    List<Telemetria> findByDtmBetweenAndDeviceOrderByDtm(Date start, Date end, Device device);
    List<Telemetria> findAllByOrderByDtm();
}
