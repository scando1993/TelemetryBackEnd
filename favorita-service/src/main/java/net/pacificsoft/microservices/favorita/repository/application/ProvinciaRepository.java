package net.pacificsoft.microservices.favorita.repository.application;


import net.pacificsoft.microservices.favorita.models.application.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long>{

}
