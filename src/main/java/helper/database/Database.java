package helper.database;

import helper.PropertiesLoader;
import services.ExceptionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Database {
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
            ExceptionService exceptionService = new ExceptionService();
            exceptionService.logging("Database", e.getMessage());
        }

        return null;
    }
}
