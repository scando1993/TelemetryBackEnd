package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Mac;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface MacRepository extends PagingAndSortingRepository<Mac, Long>{

}
