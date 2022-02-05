package ua.foxminded.herasimov.task7.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;
    private final Connection connection;
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/school";
    private static final String USER_NAME = "myuser";
    private static final String PASSWORD = "password";

    private DBConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Database Connection Creation Failed : " + e.getMessage());
            throw e;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }


}
