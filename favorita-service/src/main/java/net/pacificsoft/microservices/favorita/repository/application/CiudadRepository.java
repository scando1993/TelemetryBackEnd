package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.models.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Long>{

}
