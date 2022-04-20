package ua.foxminded.herasimov.task7.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection;
    private FileInputStream fis;
    private static Properties properties = new Properties();

    {
        try {
            fis =
                new FileInputStream(new File(this.getClass().getClassLoader().getResource("db.properties").toURI()));
            properties.load(fis);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return connection;
    }
}
