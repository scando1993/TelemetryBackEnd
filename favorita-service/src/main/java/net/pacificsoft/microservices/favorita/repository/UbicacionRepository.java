package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Ubicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends JpaRepository<Ubicacion, Long>{

}
