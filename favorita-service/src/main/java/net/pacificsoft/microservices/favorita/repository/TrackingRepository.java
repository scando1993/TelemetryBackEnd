package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface TrackingRepository extends JpaRepository<Tracking, Long>{
    Tracking findByDtm(Date dtm);
    List<Tracking> findByDtmBetween(Date start, Date end);
    List<Tracking> findByDtmBetweenAndDevice(Date start, Date end, Device device);
    List<Tracking> findByDtmGreaterThanEqualAndDtmLessThanEqual(Date start, Date end);
    List<Tracking> findByDtmLessThanEqual(Date end);
    List<Tracking> findByDtmLessThanEqualAndDevice(Date end, Device device);


}
