package net.pacificsoft.microservices.telemetry.sms.watcher;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class databaseConMongo {
    public static void realizarCargaDB(String path) throws IOException {


        String contenidoArchivo = new String(Files.readAllBytes(Paths.get(path)));
        String rutaArchivo = path;
        System.out.print(contenidoArchivo+"\n");
        DBObject archivo = new BasicDBObject("ruta_archivo",rutaArchivo).append("contenido_archivo",contenidoArchivo);

        try
        {
            // create a mongo database connection

            MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
            MongoDatabase db =mongoClient.getDatabase("prueba");
            MongoCollection<BasicDBObject> col = db.getCollection("prueba", BasicDBObject.class);
            col.insertOne((BasicDBObject) archivo);

            mongoClient.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }
}
