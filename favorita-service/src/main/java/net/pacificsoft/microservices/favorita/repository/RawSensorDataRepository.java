package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.RawSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawSensorDataRepository extends JpaRepository<RawSensorData, Long>{

}
