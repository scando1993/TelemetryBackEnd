package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.RawSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface WifiScanRepository extends JpaRepository<WifiScan, Long>{
    List<WifiScan> findByRawSensorDataOrderById(RawSensorData rawSensorData);
    //List<WifiScan> findAllAndOrderById();
    List<WifiScan> findByMacNotLikeAndRawSensorData(String mac, RawSensorData rawSensorData);
}
