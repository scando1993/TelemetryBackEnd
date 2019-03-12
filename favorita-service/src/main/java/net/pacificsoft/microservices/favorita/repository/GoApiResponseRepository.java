package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.GoApiResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoApiResponseRepository extends JpaRepository<GoApiResponse, Long>{

}
