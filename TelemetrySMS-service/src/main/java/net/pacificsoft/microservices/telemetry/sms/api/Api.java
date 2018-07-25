package net.pacificsoft.microservices.telemetry.sms.api;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.springframework.web.bind.annotation.*;
import net.pacificsoft.microservices.telemetry.sms.protocols.*;
@RestController
public class Api {

    @RequestMapping(value="/mensajes", method=RequestMethod.GET)
    public Object getMensajes() {
        return findAllSms();
    }

    @RequestMapping(value="/mensajes/last", method=RequestMethod.GET)
    public Object getMensaje() {
        return findLastSms();
    }

    private Object findAllSms() {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase db =mongoClient.getDatabase("RepoSMS");
        MongoCollection col = db.getCollection("mensajes");
        return col.find();
    }

    private Object findLastSms () {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase db =mongoClient.getDatabase("RepoSMS");
        MongoCollection col = db.getCollection("mensajes");
        return col.find().sort(new BasicDBObject("_id",-1)).first();
    }


}
