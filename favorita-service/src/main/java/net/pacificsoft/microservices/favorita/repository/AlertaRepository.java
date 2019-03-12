package net.pacificsoft.springbootcrudrest.repository;

import net.pacificsoft.springbootcrudrest.model.Alerta;
import net.pacificsoft.springbootcrudrest.model.Bodega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long>{

}
