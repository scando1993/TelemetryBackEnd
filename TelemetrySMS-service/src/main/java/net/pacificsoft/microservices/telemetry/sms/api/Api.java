package net.pacificsoft.microservices.telemetry.sms.api;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.web.bind.annotation.*;
import sun.rmi.transport.ObjectTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.apache.tomcat.jni.Time.sleep;

@RestController
public class Api {

    @RequestMapping(value="/mensajes", method=RequestMethod.GET)
    public Object getAll() {
            return findAllSms();
    }

    @RequestMapping(value="/mensajes/last", method=RequestMethod.GET)
    public Object getLast() {
        return findLastSms();
    }

    @RequestMapping(value = "/mensajes?year={year}&month={month}&day={day}", method = RequestMethod.GET)
    public Object getSpecific(@PathVariable int year,@PathVariable String month, @PathVariable int day) {
        return findSpecificDay(year,month,day);
    }

    private Object findAllSms()  {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db =mongoClient.getDatabase("RepoSMS");
            MongoCollection col = db.getCollection("mensajes");
            Object data = col.find().into(new ArrayList<Document>());
            mongoClient.close();

            return data;
    }

    private Object findLastSms () {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase db =mongoClient.getDatabase("RepoSMS");
        MongoCollection col = db.getCollection("mensajes");
        Object data = col.find().sort(new BasicDBObject("_id",-1)).first();
        mongoClient.close();
        return data;
    }

    private Object findSpecificDay (int year,String month,int day) {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase db =mongoClient.getDatabase("RepoSMS");
        MongoCollection col = db.getCollection("mensajes");
        Document query = new Document("_id", month+" "+day+", "+year);
        Object data = col.find(query);
        mongoClient.close();
        return data;
    }


}
