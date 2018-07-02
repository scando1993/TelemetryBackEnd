package net.pacificsoft.microservices.loka.core.api;

public interface LokaCoreApi {
    String loka_core_key = "d4703b4f-b4a7-4976-89f8-0ce0ceba5c50";
    String loka_core = "https://core.loka.systems";
    String loka_core_subscription = loka_core + "/api/subscription/v1/subscribe/";
    String loka_core_unsubscribe = loka_core + "/api/subscription/v1/unsubscribe/";
    String loka_core_subscription_allowed = loka_core + "/api/subscription/v1/allowed/";
    String loka_core_subscription_list = loka_core + "/api/subscription/v1/list/";
}
