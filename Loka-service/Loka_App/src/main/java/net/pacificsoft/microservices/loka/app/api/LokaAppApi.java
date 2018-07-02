package net.pacificsoft.microservices.loka.app.api;

public interface LokaAppApi {
    String loka_app = "https://loka-app.com/api";
    String loka_app_login = loka_app + "/login";
    String loka_app_list_devices = loka_app + "/deviceList";
    String loka_app_device_details = loka_app + "/deviceDetails";
}
