package ua.foxminded.herasimov.task7.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final Logger logger = LoggerFactory.getLogger(DBConnection.class);
    private static Connection connection;
    private static final Properties properties = new Properties();

    {
        try {
            FileInputStream fis =
                new FileInputStream(new File(this.getClass().getClassLoader().getResource("db.properties").toURI()));
            properties.load(fis);
        } catch (FileNotFoundException e) {
            logger.error("File input stream not initiated because of {}", e.getMessage());
        } catch (URISyntaxException e) {
            logger.error("File URL not converted to URI because of {}", e.getMessage());
        } catch (IOException e) {
            logger.error("Properties not load file input stream because of {}", e.getMessage());
        }
    }


    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(properties.getProperty("db.url"),
                                                         properties.getProperty("db.username"),
                                                         properties.getProperty("db.password"));
            }
        } catch (SQLException e) {
            logger.error("Connection not received because of {}", e.getMessage());
        }
        return connection;
    }
}
