package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.AliasLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AliasLocationRepository extends JpaRepository<AliasLocation, Long>{

}
