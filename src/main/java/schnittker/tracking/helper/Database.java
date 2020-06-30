package schnittker.tracking.helper;

import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author markus schnittker
 */
@Slf4j
public final class Database implements AutoCloseable{
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Properties properties = new PropertiesLoader().loadProperties("database.properties");
            String database = properties.getProperty("database");
            String user = properties.getProperty("user");
            String password = properties.getProperty("password");
            int port = Integer.parseInt(properties.getProperty("port"));

            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setUser(user);
            dataSource.setPassword(password);
            dataSource.setDatabaseName(database);
            dataSource.setPortNumber(port);

            connection = dataSource.getConnection();
            return connection;
        } catch (SQLException | NullPointerException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
