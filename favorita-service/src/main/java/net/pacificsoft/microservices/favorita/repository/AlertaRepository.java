package net.pacificsoft.microservices.favorita.repository;

import java.util.Date;
import java.util.List;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RepositoryRestResource()
public interface AlertaRepository extends JpaRepository<Alerta, Long>{
    List<Alerta> findByRutaAndTypeAlert(Ruta ruta, String type_alert);
    List<Alerta> findByRutaAndTypeAlertOrderByDtm(Ruta ruta, String type_alert);
    List<Alerta> findByRuta(Ruta ruta);
    List<Alerta> findBytypeAlertAndRutaOrderByDtm(String type_alert, Ruta ruta);
    List<Alerta> findByRutaOrderByDtm(Ruta ruta);
    List<Alerta> findByRutaIsNotNullOrderByDtm();
    List<Alerta> findByRutaAndDtm(Ruta ruta, Date dtm);
    List<Alerta> findByRutaAndDtmAndTypeAlert(Ruta ruta, Date dtm, String type_alert);
    List<Alerta> findByRutaAndTypeAlertAndDtm(Ruta ruta, String typeAlerta, Date dtm);
}
