package net.pacificsoft.microservices.favorita;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ThreadStartRuta extends Thread{
    
    @Autowired
    RutaRepository rutaRepository;
    List<Long> ids = new ArrayList();
    @Override
	public void run() {
            while(true){
            List<Ruta> rutas = rutaRepository.findAll();
            for (Ruta r: rutas){
                Date date = new Date();
                if(date.compareTo(r.getStart_date())>=0 &&
                   (!ids.contains(r.getId()))){
                    ids.add(r.getId());
                    r.setStatus("Activa");
                    rutaRepository.save(r);
                    String typeAlert = "inicio_ruta";
                    String mensaje = "Inicio ruta. Device: " + r.getDevice().getName() + 
                                " y producto " + r.getProducto().getName();
                    ThreadStateRuta ts = new ThreadStateRuta(r);
                    ts.saveRuta(r, typeAlert, mensaje);
                    ts.start();
                }
            }
        }
        }
}
