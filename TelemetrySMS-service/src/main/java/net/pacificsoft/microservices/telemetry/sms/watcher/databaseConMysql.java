package net.pacificsoft.microservices.telemetry.sms.watcher;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class databaseConMysql {
    public static void realizarCargaDB(String path) throws IOException {

        String contenidoArchivo = new String(Files.readAllBytes(Paths.get(path)));
        String rutaArchivo = path;
        System.out.print(contenidoArchivo);
        try
        {
            /*// create a mysql database connection
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/pruebaSpring", "root", "none1aA.");



            // create a sql date object so we can use it in our INSERT statement



            // the mysql insert statement
            //Formato de tabla usada , id auto_increment, nombrearchivo cvarchar, contenidoArchivo longtext
            String query = " insert into prueba (rutaArchivo, contenidoArchivo)"
                    + " values (?, ?)";

            // create the mysql insert preparedstatement
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, rutaArchivo);
            preparedStmt.setString (2, contenidoArchivo);
            // execute the preparedstatement
            preparedStmt.execute();

            conn.close();*/
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            System.err.println(e.getMessage());
        }

    }

}
