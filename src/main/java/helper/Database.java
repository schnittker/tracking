package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public final class Database {
    private static final Logger LOGGER = Logger.getLogger(Database.class.getName());

    public static Connection getConnection() {
        Properties properties = PropertiesLoader.loadProperties("database.properties");

        String url = properties.getProperty("url");
        String database = properties.getProperty("database");
        String parameters = properties.getProperty("parameters");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        try {
            return DriverManager.getConnection(url + database + parameters, user, password);
        } catch (SQLException e) {
            LOGGER.severe("Exception while trying to connect to the database. " + e.getMessage());
        }

        return null;
    }
}
