package net.pacificsoft.microservices.loka.api;

import com.netflix.discovery.converters.Auto;
import net.pacificsoft.microservices.loka.model.Account;
import net.pacificsoft.microservices.loka.model.Device;
import net.pacificsoft.microservices.loka.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.AccessibleObject;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;

@RestController
public class Api {
    protected static Logger logger = LoggerFactory.getLogger(Api.class.getName());

    @Autowired
    private LoadBalancerClient loadBalancer;

    public Api() {
//        accounts = new ArrayList<>();
//        accounts.add(new Account(1, 1, "111111"));
//        accounts.add(new Account(2, 2, "222222"));
//        accounts.add(new Account(3, 3, "333333"));
//        accounts.add(new Account(4, 4, "444444"));
//        accounts.add(new Account(5, 1, "555555"));
//        accounts.add(new Account(6, 2, "666666"));
//        accounts.add(new Account(7, 2, "777777"));
    }

//    @RequestMapping("/accounts/{number}")
//    public Account findByNumber(@PathVariable("number") String number) {
//        logger.info(String.format("Account.findByNumber(%s)", number));
//        return accounts.stream().filter(it -> it.getNumber().equals(number)).findFirst().get();
//    }
//
//    @RequestMapping("/accounts/customer/{customer}")
//    public List<Account> findByCustomer(@PathVariable("customer") Integer customerId) {
//        logger.info(String.format("Account.findByCustomer(%s)", customerId));
//        return accounts.stream().filter(it -> it.getCustomerId().intValue()==customerId.intValue()).collect(Collectors.toList());
//    }
//
//    @RequestMapping("/accounts")
//    public List<Account> findAll() {
//        logger.info("Account.findAll()");
//        return accounts;
//    }
//
//    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
//    public void add(Account a) {
//        logger.info(String.format("Account.add(%s)", a));
//        accounts.add(a);
//    }
    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(value = "/login")
    public ResponseEntity<Account> login(@RequestBody(required = true) Login login){
        try{
            logger.info(String.format("Loka.login(%s,%s)", login.getUsername(), login.getPassword()));
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Account> response = restTemplate.
                    exchange(LokaAPI.loka_app_login, HttpMethod.POST, new HttpEntity<>(login) ,Account.class);
            logger.info(String.format("Response received: %s",response));
            Account account = response.getBody();
            logger.info(String.format("Account received: %s", account.toString()));
            return response;

        }catch(HttpClientErrorException e){

            logger.error(String.format("Exception at: %s", e));

        }

        return null;
    }

    @RequestMapping(value = "/deviceList")
    public List<Device> listDevices(@RequestHeader(value = "Authorization") String authHeader){


        logger.info(String.format("Loka.listDevices"));
        return null;
    }

    @RequestMapping(value = "/deviceDetails")
    public Device deviceDetails(long deviceId, long startDate, long endDate){
        return null;
    }
}
