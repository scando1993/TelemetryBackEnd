package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.models.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long>{

}
