package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface StatusRepository extends JpaRepository<Status, Long>{

}
