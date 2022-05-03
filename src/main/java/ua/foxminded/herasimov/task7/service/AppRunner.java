package ua.foxminded.herasimov.task7.service;

import ua.foxminded.herasimov.task7.util.UserAsker;
import ua.foxminded.herasimov.task7.view.AppView;

import java.sql.SQLException;
import java.util.Collection;

public class AppRunner {

    private final AppService service;
    private final AppView view;
    private final UserAsker asker;

    public AppRunner(AppService service, AppView view, UserAsker asker) {
        this.service = service;
        this.view = view;
        this.asker = asker;
    }

    public void startApp() throws SQLException {
        view.showMessage(AppView.MAIN_MENU);
        Object result = chooseAndRunFunction();
        if (result instanceof Integer && (Integer) result >= 0) {
            view.showActionResult((Integer) result);
            startApp();
        }
        if (result instanceof Collection) {
            view.showCollection((Collection) result);
            startApp();
        }
    }

    private Object chooseAndRunFunction() throws SQLException {
        int functionNumber = asker.askInt();
        switch (functionNumber) {
            case 1:
                return service.findAllGroupsWithLessOrEqualStudentCount();
            case 2:
                return service.findAllStudentsRelatedToCourseWithGivenName();
            case 3:
                return service.addNewStudent();
            case 4:
                return service.deleteStudentById();
            case 5:
                return service.addStudentToCourse();
            case 6:
                return service.removeStudentFromCourse();
            default:
                return -1;
        }
    }


}
