package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.SigfoxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface SigfoxMessageRepository extends JpaRepository<SigfoxMessage, Long>{

}
