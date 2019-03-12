package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
