package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Family;
import net.pacificsoft.microservices.favorita.models.Mac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MacRepository extends JpaRepository<Mac, Long>{

}
