package helper;

import services.ExceptionService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertiesLoader {
    public static Properties loadProperties(String fileName) {
        String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(path));
            return properties;
        } catch (IOException e) {
            ExceptionService exceptionService = new ExceptionService();
            exceptionService.logging("PropertiesLoader", e.getMessage());
        }

        return null;
    }
}
