package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Mac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface MacRepository extends JpaRepository<Mac, Long>{

}
