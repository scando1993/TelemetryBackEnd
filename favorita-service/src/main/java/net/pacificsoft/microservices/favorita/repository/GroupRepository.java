package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>{

}
