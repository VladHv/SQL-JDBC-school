package ua.foxminded.herasimov.task7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/school";
    private static final String USER_NAME = "myuser";
    private static final String PASSWORD = "password";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Properties properties = createProperties();
            connection = DriverManager.getConnection(DB_URL, properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private Properties createProperties() {
        Properties prop = new Properties();
        prop.setProperty("user", USER_NAME);
        prop.setProperty("password", PASSWORD);
        return prop;
    }

}
