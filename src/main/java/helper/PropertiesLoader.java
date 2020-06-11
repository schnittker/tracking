package main.java.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
public final class PropertiesLoader {
    public static Properties loadProperties(String fileName) {
        String path = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(fileName)).getPath();

        try {
            Properties properties = new Properties();

            properties.load(new FileInputStream(path));
            return properties;
        } catch (IOException | NullPointerException e) {
            Logger.getLogger("PropertiesLoader").severe(e.getMessage());
        }

        return null;
    }
}
