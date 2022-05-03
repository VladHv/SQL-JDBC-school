package ua.foxminded.herasimov.task7;

import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.service.AppRunner;
import ua.foxminded.herasimov.task7.service.AppService;
import ua.foxminded.herasimov.task7.service.DBTestDataGenerator;
import ua.foxminded.herasimov.task7.util.DBConnection;
import ua.foxminded.herasimov.task7.util.DBScriptRunner;
import ua.foxminded.herasimov.task7.util.DataContainer;
import ua.foxminded.herasimov.task7.util.UserAsker;
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

        CourseDao courseDao = new CourseDao();
        GroupDao groupDao = new GroupDao();
        StudentDao studentDao = new StudentDao();
        DataContainer container = new DataContainer();
        Random rand = new Random();

        DBTestDataGenerator gen = new DBTestDataGenerator(studentDao, groupDao, courseDao, container, rand);
        gen.generateTestData();

        AppView view = new AppView();
        UserAsker asker = new UserAsker();

        AppService service = new AppService(view, asker, courseDao, studentDao, groupDao);
        AppRunner runner = new AppRunner(service, view, asker);
        runner.startApp();

    }
}
