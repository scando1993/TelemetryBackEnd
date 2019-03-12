package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.models.SigfoxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SigfoxMessageRepository extends JpaRepository<SigfoxMessage, Long>{

}
