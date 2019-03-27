package net.pacificsoft.microservices.favorita.repository.application;

import net.pacificsoft.microservices.favorita.models.application.Furgon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface FurgonRepository extends JpaRepository<Furgon, Long>{

}
