package net.pacificsoft.microservices.favorita;

import net.pacificsoft.microservices.favorita.models.application.Locales;
import net.pacificsoft.microservices.favorita.models.application.LocalesMac;
import net.pacificsoft.microservices.favorita.repository.application.LocalesMacRepository;
import net.pacificsoft.microservices.favorita.repository.application.LocalesRepository;
import org.slf4j.Logger;

public class SaveMacLocal {

    private String cadena;

    public SaveMacLocal() {
    }

    public String getCadena() {
        return cadena;
    }

    public void setTypeAlert(String cadena) {
        this.cadena = cadena;
    }

    public SaveMacLocal(String cadena) {
        this.cadena = cadena;
    }
    
    public static void postMacLocales(LocalesRepository localesRepository,
            LocalesMacRepository localesMacRepository, SaveMacLocal saveMacLocal, Long localid, Logger logger){
        if(localesRepository.existsById(localid)){
            String info[] = saveMacLocal.getCadena().split("\n");
            Locales local = localesRepository.findById(localid).get();
            int i=0;
            for (String s: info){
                s = s.replaceAll("\n", ""); 
                s = s.trim();
                if(i==0){
                    i+=1;
                }
                else{
                    String c[] = s.split(";");
                    if(c.length >=3){                        
                        String ssid = c[0];
                        String mac = c[1];
                        String password = c[2];
                        String elementsMac[] = mac.split(":");
                        boolean valSintax = true;
                        boolean valElem = true;
                        if(elementsMac.length != 6)
                            valElem = false;
                        if(valElem){
                            for(String e: elementsMac){
                                String b = e.trim();
                                if(b.length()!=2){
                                    valSintax = false;
                                }
                            }
                        }
                        if(valElem && valSintax){
                            LocalesMac lm = new LocalesMac(ssid, mac, password);
                            lm.setLocales(local);
                            local.getLocalesMacs().add(lm);
                            localesMacRepository.save(lm);
                        }
                    }
                }
            }
            localesRepository.save(local);
            logger.warn(saveMacLocal.getCadena());
            logger.warn("Saved locales MAC");
        }
        else{
            logger.error("Local not found");
        }
        logger.warn("Cadena "+saveMacLocal.getCadena());
        logger.warn("Saved locales MAC");
    }
}
