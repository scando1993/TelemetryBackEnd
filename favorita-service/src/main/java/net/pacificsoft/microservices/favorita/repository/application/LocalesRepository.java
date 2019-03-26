package net.pacificsoft.microservices.favorita.repository.application;

import org.springframework.stereotype.Repository;
import net.pacificsoft.microservices.favorita.models.application.Locales;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface LocalesRepository extends PagingAndSortingRepository<Locales, Long>{

}
