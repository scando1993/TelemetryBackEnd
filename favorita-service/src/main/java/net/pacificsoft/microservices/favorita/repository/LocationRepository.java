package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{

}
