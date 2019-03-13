package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, Long>{

}
