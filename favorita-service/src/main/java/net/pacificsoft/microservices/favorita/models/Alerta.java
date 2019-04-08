package net.pacificsoft.microservices.favorita.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import net.pacificsoft.microservices.favorita.models.application.Ruta;
import org.json.JSONObject;

@Entity
@Table (name = "alerta")
public class Alerta{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Column(name = "type_alert", nullable = false)
    private String typeAlert;
    
    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rutaID", nullable = true)
    private Ruta ruta;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "deviceID")
    private Device device;

    public Alerta() {
    }

    public Alerta(String type_alert, String mensaje) {
        this.typeAlert = type_alert;
        this.mensaje = mensaje;
    }

    public Alerta(long id, String type_alert, String mensaje, Ruta ruta, Device device) {
        this.id = id;
        this.typeAlert = type_alert;
        this.mensaje = mensaje;
        this.ruta = ruta;
        this.device = device;
    }
    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put("type_alert", this.typeAlert);
        json.put("mensaje",mensaje);
        return json;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getTypeAlert() {
        return typeAlert;
    }

    public void setTypeAlert(String typeAlert) {
        this.typeAlert = typeAlert;
    }

}
