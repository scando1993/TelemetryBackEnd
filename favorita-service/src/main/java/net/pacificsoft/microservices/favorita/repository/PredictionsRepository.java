package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Prediction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource()
public interface PredictionsRepository extends PagingAndSortingRepository<Prediction, Long>{

}
