package net.pacificsoft.microservices.favorita.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface WifiScanRepository extends JpaRepository<WifiScan, Long>{

}
