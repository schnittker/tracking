package main.java.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
public final class PropertiesLoader {
    public Properties loadProperties(String fileName) {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            Properties properties = new Properties();

            properties.load(inputStream);
            return properties;
        } catch (IOException | NullPointerException e) {
            Logger.getLogger("PropertiesLoader").severe(e.getMessage());
        }

        return null;
    }
}
