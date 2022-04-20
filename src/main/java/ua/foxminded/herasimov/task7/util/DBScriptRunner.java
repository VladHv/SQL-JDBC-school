package ua.foxminded.herasimov.task7.util;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.Connection;

public class DBScriptRunner {

    private Connection connection;

    public DBScriptRunner(Connection connection) {
        this.connection = connection;
    }

    public void runScript(String sqlScriptFile) {
        try {
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            ClassLoader loader = this.getClass().getClassLoader();
            File sqlScript = new File(loader.getResource(sqlScriptFile).toURI());
            BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
