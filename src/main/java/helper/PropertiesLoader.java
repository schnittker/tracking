package helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public final class PropertiesLoader {
    private static final Logger LOGGER = Logger.getLogger(PropertiesLoader.class.getName());

    public static Properties loadProperties(String fileName) {
        String path = Thread.currentThread().getContextClassLoader().getResource(fileName).getPath();
        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream(path));
            return properties;
        } catch (IOException e) {
            LOGGER.severe("Exception while trying to open the property file. " + e.getMessage());
        }

        return null;
    }
}
