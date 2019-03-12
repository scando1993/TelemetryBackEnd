package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long>{

}
