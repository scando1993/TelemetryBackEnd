package net.pacificsoft.microservices.favorita.repository.application;

import java.awt.print.Pageable;
import net.pacificsoft.microservices.favorita.models.application.Bodega;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BodegaRepository extends JpaRepository<Bodega, Long>{
    //Page<Bodega> findAllByName(@Param("id") long id, Pageable page);
}
