package net.pacificsoft.microservices.favorita.repository;

import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface TrackingRepository extends JpaRepository<Tracking, Long>{
    Tracking findByDtm(Date dtm);
    List<Tracking> findByDtmBetween(Date start, Date end);
    List<Tracking> findByDtmBetweenAndDevice(Date start, Date end, Device device);
    List<Tracking> findByDtmBetweenAndDeviceOrderByDtm(Date start, Date end, Device device);
    List<Tracking> findByDtmGreaterThanEqualAndDtmLessThanEqual(Date start, Date end);
    List<Tracking> findByDtmLessThanEqual(Date end);
    List<Tracking> findByDtmLessThanEqualAndDevice(Date end, Device device);
    List<Tracking> findByDeviceOrderByDtmDesc(Device device);
    //List<Tracking> findByDeviceOrderByDtm(@Param("device") String device);
    //List<Tracking> findAllOrderById();
    List<Tracking> findAllByOrderByDtm();
}
