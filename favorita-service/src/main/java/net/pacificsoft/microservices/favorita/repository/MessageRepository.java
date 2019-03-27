package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface MessageRepository extends JpaRepository<Message, Long>{

}
