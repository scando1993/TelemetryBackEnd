package net.pacificsoft.microservices.favorita.repository.application;

import java.util.List;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface RutaRepository extends JpaRepository<Ruta, Long>{
    List<Ruta> findByStatusIs(String status);    
    List<Ruta> findByStatusNotLike(String status);
    List<Ruta> findByStatusLike(String status);
}
