package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long>{
    Tracking findByDtm(Date dtm);
    List<Tracking> findByDtmGreaterThanEqualAndDtmLessThanEqual(Date start, Date end);

}
