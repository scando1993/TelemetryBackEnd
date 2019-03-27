package net.pacificsoft.microservices.favorita.repository.application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import net.pacificsoft.microservices.favorita.models.application.Locales;

@RepositoryRestResource()
public interface LocalesRepository extends JpaRepository<Locales, Long>{

}
