package net.pacificsoft.springbootcrudrest.repository;

import net.pacificsoft.springbootcrudrest.model.Bodega;
import net.pacificsoft.springbootcrudrest.model.Producto;
import net.pacificsoft.springbootcrudrest.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long>{

}
