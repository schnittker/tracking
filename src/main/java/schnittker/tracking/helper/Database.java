package schnittker.tracking.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author markus schnittker
 */
public final class Database implements AutoCloseable{
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Properties properties = new PropertiesLoader().loadProperties("database.properties");
            String url = properties.getProperty("url");
            String database = properties.getProperty("database");
            String parameters = properties.getProperty("parameters");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");

            connection = DriverManager.getConnection(url + database + parameters, user, password);
            return connection;
        } catch (SQLException | NullPointerException e) {
            Logger.getLogger("Database").warning(e.getMessage());
        }

        return null;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
