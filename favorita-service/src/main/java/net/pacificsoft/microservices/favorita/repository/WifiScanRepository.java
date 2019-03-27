package net.pacificsoft.microservices.favorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import net.pacificsoft.microservices.favorita.models.WifiScan;

@RepositoryRestResource()
public interface WifiScanRepository extends JpaRepository<WifiScan, Long>{

}
