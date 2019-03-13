package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Telemetria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetriaRepository extends JpaRepository<Telemetria, Long>{

}
