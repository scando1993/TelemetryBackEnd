package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.WifiScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WifiScanRepository extends JpaRepository<WifiScan, Long> {

}
