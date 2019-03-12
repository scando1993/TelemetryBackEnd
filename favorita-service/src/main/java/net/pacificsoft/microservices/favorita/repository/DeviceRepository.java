package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {

}
