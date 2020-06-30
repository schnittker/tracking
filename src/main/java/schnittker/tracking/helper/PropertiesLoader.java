package schnittker.tracking.helper;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author markus schnittker
 */
@Slf4j
public final class PropertiesLoader {
    public Properties loadProperties(String fileName) {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            Properties properties = new Properties();

            properties.load(inputStream);
            return properties;
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
        }

        return null;
    }
}
