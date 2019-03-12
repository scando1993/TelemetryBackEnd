package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
