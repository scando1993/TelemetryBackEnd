package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.MessageGuess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageGuessRepository extends JpaRepository<MessageGuess, Long>{

}
