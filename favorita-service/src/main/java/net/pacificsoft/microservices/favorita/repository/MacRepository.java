package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Mac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
//@RepositoryConfig(cacheName = "MacCache")
public interface MacRepository extends JpaRepository<Mac, Long>{
    List<Mac> findByMac (String mac);
    boolean existsByMac(String mac);

}
