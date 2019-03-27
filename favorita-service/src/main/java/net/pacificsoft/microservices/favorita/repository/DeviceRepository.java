package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@RepositoryRestResource()
public interface DeviceRepository extends JpaRepository<Device, Long>{
    List<Device> findByName(String name);
    boolean existsByName(String name);

}
