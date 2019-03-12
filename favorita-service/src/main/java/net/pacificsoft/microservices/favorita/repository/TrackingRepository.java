package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long>{

}
