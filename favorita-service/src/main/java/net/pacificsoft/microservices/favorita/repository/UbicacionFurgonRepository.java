package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.UbicacionFurgon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionFurgonRepository extends JpaRepository<UbicacionFurgon, Long>{

}
