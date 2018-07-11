package net.pacificsoft.microservices.telemetry.sms.watcher;


import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class databaseConMongo {
    public static void realizarCargaDB(String path) throws IOException {


        try
        {
            // create a mongo database connection

            String json = new String(Files.readAllBytes(Paths.get(path)));
            System.out.print(json+"\n");
            DBObject dbObject = (DBObject)JSON.parse(json);
            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db =mongoClient.getDatabase("prueba");
            MongoCollection<BasicDBObject> col = db.getCollection("prueba", BasicDBObject.class);

            col.insertOne((BasicDBObject) dbObject);

            mongoClient.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }
}
