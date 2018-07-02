package net.pacificsoft.microservices.loka.api;

public interface LokaAPI {
    String loka_app = "https://loka-app.com/api";
    String loka_app_login = loka_app + "/login";
    String loka_app_list_devices = loka_app + "/deviceList";
    String loka_app_device_details = loka_app + "deviceDetails";

    String loka_api_key = "";
    String loka_api = "https://core.loka.systems";
    String loka_api_subscription = loka_api + "/api/subscription/v1/subscribe/";
    String loka_api_unsubscribe = loka_api + "/api/subscription/v1/unsubscribe";
    String loka_api_subscribe_terminal = loka_api + "/api/subscribe_terminal";
    String loka_api_unsubscribe_terminal = loka_api + "/api/unsubscribe_terminal";
}
