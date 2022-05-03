package ua.foxminded.herasimov.task7.service;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.foxminded.herasimov.task7.util.UserAsker;
import ua.foxminded.herasimov.task7.view.AppView;

import java.sql.SQLException;

class AppRunnerTest {

    AppService service = Mockito.mock(AppService.class);

    AppView view = Mockito.mock(AppView.class);

    UserAsker asker = Mockito.mock(UserAsker.class);

    AppRunner runner = new AppRunner(service, view, asker);

    @Test
    void startApp_shouldBeRunThreeTimesAndShowMenu_whenUserInputNumberOfExistingFunctionTwiceAndServiceReturnNumberOrCollectionAndThenInputOtherNumber() throws
        SQLException {
        Mockito.when(asker.askInt()).thenReturn(1).thenReturn(1).thenReturn(0);
        Mockito.when(service.findAllGroupsWithLessOrEqualStudentCount()).thenReturn(0)
               .thenReturn(ArgumentMatchers.anyList());
        runner.startApp();
        Mockito.verify(view, Mockito.times(3)).showMessage(AppView.MAIN_MENU);
    }

    @Test
    void startApp_shouldRunOneAndShowMenu_whenUserInputNumberOtherOfExistingFunction() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(0);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.MAIN_MENU);
    }

    @Test
    void startApp_shouldCallFindAllGroupsWithLessOrEqualStudentCountServiceMethod_whenUserInputOne() throws
        SQLException {
        Mockito.when(asker.askInt()).thenReturn(1).thenReturn(0);
        runner.startApp();
        Mockito.verify(service, Mockito.times(1)).findAllGroupsWithLessOrEqualStudentCount();
    }

    @Test
    void startApp_shouldCallFindAllStudentsRelatedToCourseWithGivenNameServiceMethod_whenUserInputOne() throws
        SQLException {
        Mockito.when(asker.askInt()).thenReturn(2).thenReturn(0);
        runner.startApp();
        Mockito.verify(service, Mockito.times(1)).findAllStudentsRelatedToCourseWithGivenName();
    }

    @Test
    void startApp_shouldCallAddNewStudentServiceMethod_whenUserInputOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(3).thenReturn(0);
        runner.startApp();
        Mockito.verify(service, Mockito.times(1)).addNewStudent();
    }

    @Test
    void startApp_shouldCallDeleteStudentByIdServiceMethod_whenUserInputOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(4).thenReturn(0);
        runner.startApp();
        Mockito.verify(service, Mockito.times(1)).deleteStudentById();
    }

    @Test
    void startApp_shouldCallRemoveStudentFromCourseServiceMethod_whenUserInputOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(6).thenReturn(0);
        runner.startApp();
        Mockito.verify(service, Mockito.times(1)).removeStudentFromCourse();
    }

    @Test
    void startApp_shouldCallAddStudentToCourseServiceMethod_whenUserInputOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(5).thenReturn(0);
        runner.startApp();
        Mockito.verify(service, Mockito.times(1)).addStudentToCourse();
    }

    @Test
    void startApp_shouldShowActionResultWithParameterZero_whenFunctionOneReturnZero() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(1).thenReturn(0);
        Mockito.when(service.findAllGroupsWithLessOrEqualStudentCount()).thenReturn(0);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(0);
    }

    @Test
    void startApp_shouldShowCollection_whenFunctionOneReturnList() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(1).thenReturn(0);
        Mockito.when(service.findAllGroupsWithLessOrEqualStudentCount()).thenReturn(ArgumentMatchers.anyList());
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showCollection(ArgumentMatchers.anyList());
    }

    @Test
    void startApp_shouldShowActionResultWithParameterZero_whenFunctionTwoReturnZero() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(2).thenReturn(0);
        Mockito.when(service.findAllStudentsRelatedToCourseWithGivenName()).thenReturn(0);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(0);
    }

    @Test
    void startApp_shouldShowCollection_whenFunctionTwoReturnSet() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(2).thenReturn(0);
        Mockito.when(service.findAllStudentsRelatedToCourseWithGivenName()).thenReturn(ArgumentMatchers.anySet());
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showCollection(ArgumentMatchers.anySet());
    }

    @Test
    void startApp_shouldShowActionResultWithParameterZero_whenFunctionThreeReturnZero() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(3).thenReturn(0);
        Mockito.when(service.addNewStudent()).thenReturn(0);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(0);
    }

    @Test
    void startApp_shouldShowActionResultWithParameterOne_whenFunctionThreeReturnOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(3).thenReturn(0);
        Mockito.when(service.addNewStudent()).thenReturn(1);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(1);
    }

    @Test
    void startApp_shouldShowActionResultWithParameterZero_whenFunctionFourReturnZero() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(4).thenReturn(0);
        Mockito.when(service.deleteStudentById()).thenReturn(0);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(0);
    }

    @Test
    void startApp_shouldShowActionResultWithParameterOne_whenFunctionFourReturnOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(4).thenReturn(0);
        Mockito.when(service.deleteStudentById()).thenReturn(1);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(1);
    }

    @Test
    void startApp_shouldShowActionResultWithParameterZero_whenFunctionFiveReturnZero() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(5).thenReturn(0);
        Mockito.when(service.addStudentToCourse()).thenReturn(0);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(0);
    }

    @Test
    void startApp_shouldShowActionResultWithParameterOne_whenFunctionFiveReturnOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(5).thenReturn(0);
        Mockito.when(service.addStudentToCourse()).thenReturn(1);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(1);
    }

    @Test
    void startApp_shouldShowActionResultWithParameterZero_whenFunctionSixReturnZero() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(6).thenReturn(0);
        Mockito.when(service.removeStudentFromCourse()).thenReturn(0);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(0);
    }

    @Test
    void startApp_shouldShowActionResultWithParameterOne_whenFunctionSixReturnOne() throws SQLException {
        Mockito.when(asker.askInt()).thenReturn(6).thenReturn(0);
        Mockito.when(service.removeStudentFromCourse()).thenReturn(1);
        runner.startApp();
        Mockito.verify(view, Mockito.times(1)).showActionResult(1);
    }
}
