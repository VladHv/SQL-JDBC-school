package ua.foxminded.herasimov.task7.util;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBScriptRunner {

    public void runScript(String sqlScriptFileName) {
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            ClassLoader loader = this.getClass().getClassLoader();
            File sqlScript = new File(loader.getResource(sqlScriptFileName).toURI());
            BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException | URISyntaxException | SQLException e) {
            e.printStackTrace();
        }

    }

}
