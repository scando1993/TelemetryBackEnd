package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {
    public Alert findByType_alert(String alert);
}
