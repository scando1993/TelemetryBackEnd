package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@RepositoryRestResource()
public interface TrackingRepository extends JpaRepository<Tracking, Long>{
    Tracking findByDtm(Date dtm);
    List<Tracking> findByDtmBetween(Date start, Date end);
    List<Tracking> findByDtmBetweenAndDevice(Date start, Date end, Device device);
    List<Tracking> findByDtmGreaterThanEqualAndDtmLessThanEqual(Date start, Date end);
    List<Tracking> findByDtmLessThanEqual(Date end);
    List<Tracking> findByDtmLessThanEqualAndDevice(Date end, Device device);


}
