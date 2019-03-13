package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long>{

}
