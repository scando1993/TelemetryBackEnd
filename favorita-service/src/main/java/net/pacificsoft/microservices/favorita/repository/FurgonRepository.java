package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Furgon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FurgonRepository extends JpaRepository<Furgon, Long>{

}
