package ua.foxminded.herasimov.task7;

import ua.foxminded.herasimov.task7.service.AppRunner;
import ua.foxminded.herasimov.task7.service.BDTestDataGenerator;
import ua.foxminded.herasimov.task7.util.DBScriptRunner;

public class SQLSchool {
    public static void main(String[] args) {

        String sqlScriptFileName = "create_tables.sql";

        DBScriptRunner creator = new DBScriptRunner();
        creator.runScript(sqlScriptFileName);

        BDTestDataGenerator gen = new BDTestDataGenerator();
        gen.generateTestData();

        AppRunner runner = new AppRunner();
        runner.startApp();
    }
}
