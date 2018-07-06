package net.pacificsoft.microservices.telemetry.sms.watcher;

/*
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
*/

import java.io.IOException;

public class databaseConMongo {
    /*public static void realizarCargaDB(String path) throws IOException {


        //String contenidoArchivo = new String(file.toString());
        //String nombreArchivo = file.getName();
        //System.out.print(contenidoArchivo);
        //DBObject archivo = new BasicDBObject("nombre_archivo",nombreArchivo).append("contenido_archivo",contenidoArchivo);
        try
        {
            // create a mysql database connection

            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db =mongoClient.getDatabase("prueba");
            MongoCollection<BasicDBObject> col = db.getCollection("prueba", BasicDBObject.class);
            //col.insertOne((BasicDBObject) archivo);

            mongoClient.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }
*/
}
