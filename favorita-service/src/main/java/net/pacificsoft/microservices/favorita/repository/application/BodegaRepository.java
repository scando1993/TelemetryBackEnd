package net.pacificsoft.microservices.favorita.repository.application;

import net.pacificsoft.microservices.favorita.models.application.Bodega;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface BodegaRepository extends PagingAndSortingRepository<Bodega, Long>{
    //Page<Bodega> findAllByName(@Param("id") long id, Pageable page);
}
