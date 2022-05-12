package ua.foxminded.herasimov.task7;

import ua.foxminded.herasimov.task7.dao.impl.CourseDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.GroupDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.StudentDaoImpl;
import ua.foxminded.herasimov.task7.service.AppRunner;
import ua.foxminded.herasimov.task7.service.AppService;
import ua.foxminded.herasimov.task7.service.DBTestDataGenerator;
import ua.foxminded.herasimov.task7.util.DBConnection;
import ua.foxminded.herasimov.task7.util.DBScriptRunner;
import ua.foxminded.herasimov.task7.util.DataContainer;
import ua.foxminded.herasimov.task7.util.Reader;
import ua.foxminded.herasimov.task7.view.AppView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

public class SQLSchool {
    public static void main(String[] args) throws SQLException {

        try (Connection connection = new DBConnection().getConnection()) {
            String sqlScriptFile = "script/create_tables.sql";

            DBScriptRunner scriptRunner = new DBScriptRunner(connection);
            scriptRunner.runScript(sqlScriptFile);
        }

        CourseDaoImpl courseDao = new CourseDaoImpl();
        GroupDaoImpl groupDao = new GroupDaoImpl();
        StudentDaoImpl studentDao = new StudentDaoImpl();
        DataContainer container = new DataContainer();
        Random rand = new Random();

        DBTestDataGenerator gen = new DBTestDataGenerator(studentDao, groupDao, courseDao, container, rand);
        gen.generateTestData();

        AppView view = new AppView();
        Reader reader = new Reader();

        AppService service = new AppService(view, reader, courseDao, studentDao, groupDao);
        AppRunner runner = new AppRunner(service, view, reader);
        runner.startApp();

    }
}
