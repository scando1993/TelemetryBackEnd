package net.pacificsoft.microservices.favorita;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.Device;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import net.pacificsoft.microservices.favorita.models.Tracking;
import net.pacificsoft.microservices.favorita.models.application.Producto;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.TelemetriaRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;

public class ThreadStateRuta extends Thread{
    
    private Ruta ruta;
    RutaRepository rutaRepository;
    DeviceRepository deviceRepository;
    AlertaRepository alertaRepository;
    TrackingRepository trackingRepository;
    TelemetriaRepository telemetriaRepository;
    
    public ThreadStateRuta(Long id) {
        ruta = rutaRepository.findById(id).get();
    }
    
    @Override
    public void run() {
        boolean process = true;
        while(process){
            try {
                Thread.sleep(Variables.time_check);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadStateRuta.class.getName()).log(Level.SEVERE, null, ex);
            }
            long valErr = 0;
            Telemetria tAnterior = null;
            Device d = ruta.getDevice();
            Producto p = ruta.getProducto();
            List<Tracking> trackings = trackingRepository.findByDtmBetweenAndDevice(ruta.getStart_date(), ruta.getEnd_date(),ruta.getDevice());
            List<Telemetria> telemetrias = telemetriaRepository.findByDtmBetweenAndDevice(ruta.getStart_date(), ruta.getEnd_date(),ruta.getDevice());
            float temp_max = p.getTemp_max();
            float temp_min = p.getTemp_min();
            float temp_max_ideal = p.getTemp_max_ideal();
            float temp_min_ideal = p.getTemp_min_ideal();
            for (Telemetria t: telemetrias){
                if(t.getValue() >= temp_max || t.getValue() <= temp_min){
                    if(tAnterior != null) {
                        valErr = valErr + (t.getDtm().getTime() - tAnterior.getDtm().getTime());
                    }
                }
                else{
                    tAnterior = new Telemetria(t.getDtm(), t.getName(), t.getValue());
                    valErr = 0;
                }
                if (valErr >= 3600){
                    String typeAlert = "temperatura_limite";
                    String mensaje = "Temperatura del producto " + p.getName() + 
                                    " esta fuera de los límites";
                    Alerta alert = new Alerta(typeAlert, mensaje);
                    alert.setDevice(d);
                    alert.setRuta(ruta);
                    d.getAlertas().add(alert);
                    ruta.getAlertas().add(alert);
                    alertaRepository.save(alert);
                    deviceRepository.save(d);
                    rutaRepository.save(ruta);
                    process = false;
                    break;
                }
            }
            /*if(alertaRepository.findByRutaAndTypeAlert(ruta, "en_ruta").size()==0){
                String typeAlert = "en_ruta";
                String mensaje = "El device " + d.getName() + 
                                " asociado con el producto " + p.getName() + 
                                " está en ruta";
                Alerta alert = new Alerta(typeAlert, mensaje);
                alert.setDevice(d);
                alert.setRuta(ruta);
                d.getAlertas().add(alert);
                ruta.getAlertas().add(alert);
                alertaRepository.save(alert);
                deviceRepository.save(d);
                rutaRepository.save(ruta);
            }*/
            if(alertaRepository.findByRutaAndTypeAlert(ruta, "inicio_ruta").size()==0){
                String typeAlert = "inicio_ruta";
                String mensaje = "Inicio ruta. Device: " + d.getName() + 
                                " y producto " + p.getName();
                Alerta alert = new Alerta(typeAlert, mensaje);
                alert.setDevice(d);
                alert.setRuta(ruta);
                d.getAlertas().add(alert);
                ruta.getAlertas().add(alert);
                alertaRepository.save(alert);
                deviceRepository.save(d);
                rutaRepository.save(ruta);
            }
        }
    }
}