package net.pacificsoft.microservices.favorita.repository;

import java.util.Date;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import net.pacificsoft.microservices.favorita.models.Device;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface RawSensorDataRepository extends JpaRepository<RawSensorData, Long>{
    List<RawSensorData> findByEpochDateTimeBetweenAndDeviceOrderByEpochDateTimeDesc(Date start, Date end, Device device);
    List<RawSensorData> findByDeviceOrderByDtm(Device device);
}
