package main.java.services;

import main.java.helper.PropertiesLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
public class ExceptionService {

    private final Properties properties;

    public ExceptionService() {
        properties = new PropertiesLoader().loadProperties("application.properties");
    }

    public void logging(String className, String message) {
        boolean debugMode = Boolean.parseBoolean(properties.getProperty("debug_mode"));

        if(debugMode) {
            printLogging(className, message);
        } else {
            writeLogFile(className, message);
        }
    }

    private void printLogging(String className, String message) {
        Logger.getLogger(className).severe(message);
    }

    private void writeLogFile(String className, String message) {
        String path = properties.getProperty("debug_file_path");
        File filePath = new File(path);
        try(FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            if(!filePath.exists()) {
                filePath.createNewFile();
            }

            printWriter.write(className + ": " + message + "\n");
        } catch (IOException e) {
            printLogging(this.getClass().getName(), e.getMessage());
        }
    }
}
