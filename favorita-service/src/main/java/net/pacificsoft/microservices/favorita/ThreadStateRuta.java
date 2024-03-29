package net.pacificsoft.microservices.favorita;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import net.pacificsoft.microservices.favorita.models.Alerta;
import net.pacificsoft.microservices.favorita.models.RawSensorData;
import net.pacificsoft.microservices.favorita.models.Telemetria;
import net.pacificsoft.microservices.favorita.models.WifiScan;
import net.pacificsoft.microservices.favorita.models.application.LocalesMac;
import net.pacificsoft.microservices.favorita.models.application.Producto;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import net.pacificsoft.microservices.favorita.repository.AlertaRepository;
import net.pacificsoft.microservices.favorita.repository.DeviceRepository;
import net.pacificsoft.microservices.favorita.repository.RawSensorDataRepository;
import net.pacificsoft.microservices.favorita.repository.TelemetriaRepository;
import net.pacificsoft.microservices.favorita.repository.TrackingRepository;
import net.pacificsoft.microservices.favorita.repository.application.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

//@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ThreadStateRuta extends Thread{
    
    private Ruta ruta;
    
    @Autowired
    RutaRepository rutaRepository;
    
    @Autowired
    DeviceRepository deviceRepository;
    
    @Autowired
    AlertaRepository alertaRepository;
    
    @Autowired
    TrackingRepository trackingRepository;
    
    @Autowired
    TelemetriaRepository telemetriaRepository;
    
    @Autowired
    RawSensorDataRepository rawSensorRepository;
    
    
    private Logger logger ;

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }
    
    
    
    public ThreadStateRuta(Ruta ruta, RutaRepository rutaRepository, DeviceRepository deviceRepository,
                           AlertaRepository alertaRepository, TrackingRepository trackingRepository, 
                           TelemetriaRepository telemetriaRepository, RawSensorDataRepository rawSensorRepository) {
        this.ruta = ruta;
        this.rutaRepository = rutaRepository;
        this.deviceRepository = deviceRepository;
        this.alertaRepository = alertaRepository;
        this.trackingRepository = trackingRepository;
        this.telemetriaRepository = telemetriaRepository;
        this.rawSensorRepository = rawSensorRepository;
    }
    
    @Override
    public void run() {
        boolean process = true;
        boolean fin = true;
        Date start_date = ruta.getStart_date();
        while(process && fin){
            try {
                Thread.sleep(Variables.time_check);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            long valErr = 0;
            Telemetria tAnterior = null;
            Producto p = ruta.getProducto();
            List<Telemetria> telemetrias = telemetriaRepository.findByDtmBetweenAndDeviceOrderByDtm(start_date, ruta.getEnd_date(),ruta.getDevice());
            float temp_max = p.getTemp_max();
            float temp_min = p.getTemp_min();
            float temp_max_ideal = p.getTemp_max_ideal();
            float temp_min_ideal = p.getTemp_min_ideal();
            for (Telemetria t: telemetrias){
                double temp = t.getValue();
                if((temp >= temp_max_ideal && temp <= temp_max) ||
                   (temp <= temp_min_ideal && temp >= temp_min)){
                    if(ruta.getProducto()!=null){
                        String typeAlert = "temperatura_limite_ideales";
                        String mensaje = "Ruta " + ruta.getId() + ". Temperatura del producto " + p.getName() + 
                                        " esta fuera de los límites ideales con una temperatura de "+temp+" grados, temperatura máxima ideal: "+temp_max_ideal
                                        +", temperatura mínima ideal: "+temp_min_ideal;
                        ruta.setStatus("No ideal");
                        if(alertaRepository.findByDeviceAndTypeAlertAndDtm(ruta.getDevice(), typeAlert, t.getDtm()).size() == 0)
                            saveRuta(ruta, typeAlert, mensaje, t.getDtm());
                    }
                }
                else if(temp >= temp_max || temp <= temp_min){
                    if(tAnterior != null) {
                        valErr = valErr + (t.getDtm().getTime() - tAnterior.getDtm().getTime());
                    }
                    tAnterior = new Telemetria(t.getDtm(), t.getName(), t.getValue());
                }
                else{
                    tAnterior = new Telemetria(t.getDtm(), t.getName(), t.getValue());
                    valErr = 0;
                    start_date = new Date();
                }
                if (valErr >= 3600){
                    if(ruta.getProducto()!=null){
                        String typeAlert = "temperatura_limite_maximas";
                        String mensaje = "Ruta " + ruta.getId() + ". Temperatura del producto " + p.getName() + 
                                        " esta fuera de los límites máximos con una temperatura de "+temp+" grados, temperatura máxima: "+temp_max
                                +", temperatura mínima: "+temp_min;
                        ruta.setStatus("No efectiva");
                        if(alertaRepository.findByDeviceAndTypeAlertAndDtm(ruta.getDevice(), typeAlert, t.getDtm()).size() == 0)
                            saveRuta(ruta, typeAlert, mensaje, t.getDtm());
                    }
                    valErr = 0;
                    break;
                }
            }
            if(process){
                List<RawSensorData> datas = rawSensorRepository.findByEpochDateTimeBetweenAndDeviceOrderByEpochDateTimeDesc(
                                                start_date, ruta.getEnd_date(), ruta.getDevice());
                if(datas.size()>0){
                    RawSensorData rw = datas.get(0);
                    Set<LocalesMac> localesMacs = ruta.getLocalFin().getLocalesMacs();
                    for(WifiScan ws: rw.getWifiScans()){
                        for(LocalesMac lm: localesMacs){
                            if(lm.getMac().equals(ws.getMac())){
                                fin = false;
                                String typeAlert = "fin_ruta";
                                if(ruta.getFurgon() != null && (alertaRepository.findByDeviceAndTypeAlertAndDtm(ruta.getDevice(), typeAlert, telemetrias.get(telemetrias.size()-1).getDtm()).size() == 0)){
                                    String mensaje = "Ruta " + ruta.getId() + ". Ha completado su ruta el furgon " + ruta.getFurgon().getName();
                                    ruta.setStatus("Finalizado");
                                    saveRuta(ruta, typeAlert, mensaje, telemetrias.get(telemetrias.size()-1).getDtm());
                                }
                                break;
                            }
                        }
                    }
                }
                if(fin && (new Date()).compareTo(ruta.getEnd_date())>=0){
                    fin = false;
                    String typeAlert = "fin_ruta";
                    if(ruta.getFurgon() != null && (alertaRepository.findByDeviceAndTypeAlertAndDtm(ruta.getDevice(), typeAlert, ruta.getEnd_date()).size() == 0)){
                        String mensaje = "Ruta " + ruta.getId() + ". Ha terminado su ruta el furgon " + ruta.getFurgon().getName();
                        ruta.setStatus("Finalizado");
                        saveRuta(ruta, typeAlert, mensaje, ruta.getEnd_date());
                    }
                    break;
                }
            }
        }
        logger.warn("Ruta with id "+ruta.getId() + " finished");

    }
     
    public void saveRuta(Ruta ruta, String typeAlert, String mensaje, Date date){
        Alerta alert = new Alerta(typeAlert, mensaje, date);
                alert.setDevice(ruta.getDevice());
                alert.setRuta(ruta);
                ruta.getDevice().getAlertas().add(alert);
                ruta.getAlertas().add(alert);
                alertaRepository.save(alert);
                deviceRepository.save(ruta.getDevice());
                rutaRepository.save(ruta);
    } 
}
