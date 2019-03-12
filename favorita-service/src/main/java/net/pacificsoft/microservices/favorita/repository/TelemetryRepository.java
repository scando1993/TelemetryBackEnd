package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Telemetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelemetryRepository extends JpaRepository<Telemetry, Long> {

}
