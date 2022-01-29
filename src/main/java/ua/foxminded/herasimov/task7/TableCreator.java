package ua.foxminded.herasimov.task7;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class TableCreator {

    private final DBConnection dbConnection = new DBConnection();

    public void createEmptyTables(String sqlScriptFileName) {
        try (Connection connection = dbConnection.getConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            ClassLoader loader = this.getClass().getClassLoader();
            File sqlScript = new File(loader.getResource(sqlScriptFileName).toURI());
            BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
            scriptRunner.runScript(reader);
        } catch (SQLException | FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }

    }

}
