package net.pacificsoft.microservices.favorita;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;

public class ThreadStartRuta extends Thread{
    
    RutaRepository rutaRepository;
    List<Long> ids = new ArrayList();
    @Override
	public void run() {
            List<Ruta> rutas = rutaRepository.findAll();
            for (Ruta r: rutas){
                Date date = new Date();
                if(date.compareTo(r.getStart_date())>=0 &&
                   (!ids.contains(r.getId()))){
                    ids.add(r.getId());
                    ThreadStateRuta ts = new ThreadStateRuta(r.getId());
                    ts.start();
                }
            }
        }
}
