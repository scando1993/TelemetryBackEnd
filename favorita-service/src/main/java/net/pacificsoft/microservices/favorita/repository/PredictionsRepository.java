package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PredictionsRepository extends JpaRepository<Prediction, Long> {

}
