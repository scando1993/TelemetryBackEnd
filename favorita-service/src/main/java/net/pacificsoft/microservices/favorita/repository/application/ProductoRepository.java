package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Bodega;
import net.pacificsoft.microservices.favorita.models.Producto;
import net.pacificsoft.microservices.favorita.models.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
