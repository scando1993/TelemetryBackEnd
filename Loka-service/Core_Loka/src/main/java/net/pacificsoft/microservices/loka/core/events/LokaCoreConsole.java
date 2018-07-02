package net.pacificsoft.microservices.loka.core.events;

import com.thoughtcreator.iot.api.ApiClient;
import com.thoughtcreator.iot.api.ApiManager;
import com.thoughtcreator.iot.api.exceptions.ConnectionFailedException;
import com.thoughtcreator.iot.api.exceptions.InvalidUsernameOrPasswordException;
import com.thoughtcreator.iot.api.exceptions.UnauthorizedAccessException;
import com.thoughtcreator.iot.api.messages.*;
import com.thoughtcreator.iot.api.terminal.Terminal;
import com.thoughtcreator.iot.api.terminal.TerminalEventHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class LokaCoreConsole implements TerminalEventHandler, ApiClient {
    private static Logger logger = LogManager.getLogger(LokaCoreConsole.class);

    private static Map<Long,Terminal> terminals = new HashMap<>(1);
    private static ApiManager apiManager = ApiManager.instance;
    private final static LokaCoreConsole console = new LokaCoreConsole();

    public int received = 0;

    static List<Long> terminalIds = new ArrayList<>(1);
    public static void init(String url, String token, String[] devices) {

        if (devices.length < 1) {
            logger.debug("Usage: <server> <token> <device_id>[,<device_id 2>[...,<device_id n>]]");
            logger.debug("Example: core.loka.systems hsajk217809-asd109u 123456789,123456788");
            return;
        }
//        Runtime runtime = Runtime.getRuntime();
//        runtime.addShutdownHook(new Thread() {
//            public void run() {
//                System.out.println("Shutting down Loka Demo.");
//                try {
//                    Iterator<Map.Entry<Long, Terminal>> it = terminals.entrySet().iterator();
//                    while (it.hasNext()) {
//                        Map.Entry<Long,Terminal> e = it.next();
//                        if (e.getValue() != null) {
//                            apiManager.removeTerminal(e.getValue());
//                        }
//                    }
//                } catch (Exception e) { }
//            }
//        });
        for (String terminal : devices) {
            terminalIds.add(Long.parseLong(terminal));
        }

        try {
            apiManager.login(url, token);
        } catch (URISyntaxException e1) {

            logger.error("Error un URI!!!");
            e1.printStackTrace();
            return;
        } catch (UnauthorizedAccessException e1) {
            logger.error("Unauthorized !!");
            e1.printStackTrace();
            return;
        } catch (ConnectionFailedException e1) {
            logger.error("Connection failed !!");
            e1.printStackTrace();
            return;
        } catch (InvalidUsernameOrPasswordException e1) {
            logger.error("Error user failed !!");
            e1.printStackTrace();
            return;
        }

        try {
            for (Long terminalId : terminalIds) {
                Terminal t = new Terminal(terminalId);
                t.setEventHandler(console);
                apiManager.addTerminal(t);
                terminals.put(terminalId, t);
            }
            apiManager.startReceivingEvents(console);

            while (true) {
                Thread.sleep(500);
            }

        } catch (Exception e) {
            logger.error("Exception " + e.toString());
            return;
        }

    }


    @Override
    public void onRegister(Terminal terminal, ControlMessage message) {
        System.out.println("Received Register from " + terminal.getId());

    }

    @Override
    public void onGpio(Terminal terminal, GpioMessage message) {
        System.out.println("Received GPIO value from " + terminal.getId() + " in port " + message.getGpio().getPort() + " with value " + message.getGpio().getValue());
    }

    @Override
    public void onDigital(Terminal terminal, DigitalMessage message) {
        System.out.println("Received digital message from " + terminal.getId() + " with value " + message.getDigital().getValue());
    }

    @Override
    public void onAnalog(Terminal terminal, AnalogMessage message) {
        System.out.println("Received analog value from " + terminal.getId() + " in port " + message.getAnalog().getPort() + " with value " + message.getAnalog().getValue());

    }

    @Override
    public void onGps(Terminal terminal, GpsMessage message) {
        System.out.println("Received GPS value from " + terminal.getId() + " with latitude " + message.getGps().getLatitude() + " and longitude " + message.getGps().getLongitude());

    }

    @Override
    public void onUnknownMessage(Terminal terminal, String message) {
        System.out.println("UNKNOWN MESSAGE ("+ message + ") from terminal " + terminal);
        System.out.println(message);

    }

    @Override
    public void onError(Exception ex) {
        logger.error("ERROR " + ex.getMessage());
        ex.printStackTrace();

    }

    public void onUnknownTerminal(long id) {
        logger.error("UNKNOWN TERMINAL " + id);

    }

    public void onWifi(Terminal terminal, WifiMessage message) {
        System.out.println("Received Wifi message: " + message.toString());
    }


    public void onNetworkInformation(Terminal terminal,
                                     NetworkInformationMessage message) {
        System.out.println("Received NetworkInformation message: " + message.toString());

    }

    public void onLocation(Terminal terminal, LocationMessage message) {
        System.out.println("Received Location value from " + terminal.getId() + " with latitude " + message.getLocation().getLatitude() + " and longitude " + message.getLocation().getLongitude());
    }

}