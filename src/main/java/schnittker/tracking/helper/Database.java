package schnittker.tracking.helper;

import schnittker.tracking.services.ExceptionLoggingService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author markus schnittker
 */
public final class Database {
    public static Connection getConnection() {
        try {
            Properties properties = new PropertiesLoader().loadProperties("database.properties");
            String url = properties.getProperty("url");
            String database = properties.getProperty("database");
            String parameters = properties.getProperty("parameters");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            return DriverManager.getConnection(url + database + parameters, user, password);
        } catch (SQLException | NullPointerException e) {
            ExceptionLoggingService exceptionLoggingService = new ExceptionLoggingService();
            exceptionLoggingService.logging("Database", e.getMessage());
        }

        return null;
    }
}
