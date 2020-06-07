package services;

import helper.PropertiesLoader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Logger;

public class ExceptionService {

    private final Properties properties;

    public ExceptionService() {
        properties = PropertiesLoader.loadProperties("application.properties");
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

        File file = new File(path);
        if(!file.exists()) {
            try {
                new FileOutputStream(path, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try(FileWriter fileWriter = new FileWriter(path, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            PrintWriter printWriter = new PrintWriter(bufferedWriter)) {

            printWriter.write(className + ": " + message);
        } catch (IOException e) {
            printLogging(this.getClass().getName(), e.getMessage());
        }
    }
}
