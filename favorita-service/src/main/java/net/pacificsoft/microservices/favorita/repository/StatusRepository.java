package net.pacificsoft.springbootcrudrest.repository;

import net.pacificsoft.springbootcrudrest.model.Bodega;
import net.pacificsoft.springbootcrudrest.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long>{

}
