package net.pacificsoft.springbootcrudrest.repository;

import net.pacificsoft.springbootcrudrest.model.Bodega;
import net.pacificsoft.springbootcrudrest.model.Telemetria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaRepository extends JpaRepository<Telemetria, Long>{

}
