package net.pacificsoft.springbootcrudrest.repository;

import net.pacificsoft.springbootcrudrest.model.RawSensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawSensorDataRepository extends JpaRepository<RawSensorData, Long>{

}
