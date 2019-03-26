package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Telemetria;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface TelemetriaRepository extends PagingAndSortingRepository<Telemetria, Long>{
}
