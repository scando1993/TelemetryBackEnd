package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.LocationPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationPriorityRepository extends JpaRepository<LocationPriority, Long>{

}
