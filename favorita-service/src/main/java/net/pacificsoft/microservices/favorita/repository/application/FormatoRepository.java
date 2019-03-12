package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Formato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatoRepository extends JpaRepository<Formato, Long>{
        
}
