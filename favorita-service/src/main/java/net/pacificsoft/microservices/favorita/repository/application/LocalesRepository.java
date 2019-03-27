package net.pacificsoft.microservices.favorita.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import net.pacificsoft.microservices.favorita.models.application.Locales;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface LocalesRepository extends JpaRepository<Locales, Long>{

}
