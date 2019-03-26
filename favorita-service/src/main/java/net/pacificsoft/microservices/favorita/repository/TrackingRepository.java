package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource()
public interface TrackingRepository extends PagingAndSortingRepository<Tracking, Long>{

}
