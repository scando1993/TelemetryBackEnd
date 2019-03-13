package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.LocationGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationGroupRepository extends JpaRepository<LocationGroup, Long>{

}
