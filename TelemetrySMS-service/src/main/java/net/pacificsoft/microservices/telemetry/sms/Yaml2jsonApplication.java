package net.pacificsoft.microservices.telemetry.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;


public class Yaml2jsonApplication {

    public static void convert2json(String sourceFilePath) {
        //Get File Path


        //Load File content
        AtomicReference<String> content = new AtomicReference<String>();
        try {
            content.set(readYamlFile(sourceFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Json string
        String jsonStr = null;
        try {
            jsonStr = convertYamlToJson(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Print json file
        sourceFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 3);
        sourceFilePath = sourceFilePath + "json";

        try {
            FileWriter fileWriter = new FileWriter(sourceFilePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(jsonStr);
            bufferedWriter.close();
            System.out.println("Success " + sourceFilePath);
        } catch (IOException err) {
            System.out.println("Error writing to file: " + err);
        }

    }

    private static String readYamlFile(String pathname) throws IOException {

        File file = new File(pathname);
        StringBuilder fileContents = new StringBuilder((int) file.length());
        Scanner scanner = new Scanner(file);
        String lineSeparator = System.getProperty("line.separator");

        try {
            while (scanner.hasNextLine()) {
                fileContents.append(scanner.nextLine() + lineSeparator);
            }
            return fileContents.toString();
        } finally {
            scanner.close();
        }
    }

    private static String convertYamlToJson(String yaml) throws IOException {

        ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
        Object obj = yamlReader.readValue(yaml, Object.class);

        ObjectMapper jsonWriter = new ObjectMapper();
        return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    }
}

//	private static String getPath(){
//
//				JFileChooser chooser = new JFileChooser();
//				chooser.setCurrentDirectory(new File("."));
//				chooser.setDialogTitle("choosertitle");
//				chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//				chooser.setAcceptAllFileFilterUsed(true);
//
//				if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//					System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
//					System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
//					String ruta=new String(chooser.getSelectedFile().toString());
//					return ruta;
//				} else {
//					System.out.println("No Selection ");
//					return null;
//				}
//			}
//    }

