package ua.foxminded.herasimov.task7;

import ua.foxminded.herasimov.task7.service.AppRunner;
import ua.foxminded.herasimov.task7.service.DBTestDataGenerator;
import ua.foxminded.herasimov.task7.util.DBConnection;
import ua.foxminded.herasimov.task7.util.DBScriptRunner;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLSchool {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = new DBConnection().getConnection()) {
            String sqlScriptFile = "script/create_tables.sql";

            DBScriptRunner scriptRunner = new DBScriptRunner(connection);
            scriptRunner.runScript(sqlScriptFile);
        }

        try (Connection connection = new DBConnection().getConnection()) {
            DBTestDataGenerator gen = new DBTestDataGenerator(connection);
            gen.generateTestData();
        }

        try (Connection connection = new DBConnection().getConnection()) {
            AppRunner runner = new AppRunner(connection);
            runner.startApp();
        }
    }
}
