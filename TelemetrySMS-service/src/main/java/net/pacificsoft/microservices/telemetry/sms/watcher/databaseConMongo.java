package net.pacificsoft.microservices.telemetry.sms.watcher;


import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import net.pacificsoft.microservices.telemetry.sms.protocols.LoadSms;
import net.pacificsoft.microservices.telemetry.sms.protocols.SMS;
import org.bson.Document;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;

public class databaseConMongo {
    public static void realizarCargaDB(String path) throws IOException {


        try
        {
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db =mongoClient.getDatabase("RepoSMS");
            MongoCollection col = db.getCollection("mensajes");
            // create a mongo database connection
            SMS sms = LoadSms.SmsProcessor();
            sms.setId(new Timestamp(System.currentTimeMillis()));
            System.out.printf(sms.toString());
            Gson gson = new Gson();
            String json = gson.toJson(sms);
            DBObject dbObject = (DBObject)JSON.parse(json);
            Document document = Document.parse(json);
            col.insertOne(document);



//            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//            Tweet t=new Tweet();
//            t.setId(100);
//            t.setName("Ghorbani");
//            DBCollection collection = null ;
//            collection = db.getCollection("test");
//            collection.save(t);

            mongoClient.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }
}
