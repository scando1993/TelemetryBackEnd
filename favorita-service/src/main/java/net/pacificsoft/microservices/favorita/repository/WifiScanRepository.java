package net.pacificsoft.microservices.favorita.repository;

import org.springframework.stereotype.Repository;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface WifiScanRepository extends PagingAndSortingRepository<WifiScan, Long>{

}
