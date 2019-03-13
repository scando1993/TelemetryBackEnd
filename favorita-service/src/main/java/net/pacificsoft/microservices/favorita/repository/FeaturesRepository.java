package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Features;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeaturesRepository extends JpaRepository<Features, Long>{

}
