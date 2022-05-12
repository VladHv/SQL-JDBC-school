package ua.foxminded.herasimov.task7.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.foxminded.herasimov.task7.dao.impl.CourseDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.GroupDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.StudentDaoImpl;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.Reader;
import ua.foxminded.herasimov.task7.view.AppView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class AppServiceTest {

    AppView view = Mockito.mock(AppView.class);
    Reader reader = Mockito.mock(Reader.class);
    CourseDaoImpl courseDao = Mockito.mock(CourseDaoImpl.class);
    StudentDaoImpl studentDao = Mockito.mock(StudentDaoImpl.class);
    GroupDaoImpl groupDao = Mockito.mock(GroupDaoImpl.class);

    AppService service = new AppService(view, reader, courseDao, studentDao, groupDao);

    @Test
    void removeStudentFromCourse_shouldShowMessageAskingStudentId_whenRunMethod() throws SQLException {
        service.removeStudentFromCourse();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.STUDENT_ID);
    }

    @Test
    void removeStudentFromCourse_shouldRequestCourseDaoToGetCoursesByStudentIdFive_whenUserInputStudentIdFive() throws
        SQLException {
        Mockito.when(reader.askInt()).thenReturn(5);
        service.removeStudentFromCourse();
        Mockito.verify(courseDao, Mockito.times(1)).getCoursesByStudentId(5);
    }

    @Test
    void removeStudentFromCourse_shouldReturnZero_whenCourseDaoReturnEmptySetOfCourses() throws SQLException {
        Mockito.when(courseDao.getCoursesByStudentId(ArgumentMatchers.anyInt())).thenReturn(ArgumentMatchers.anySet());
        Assertions.assertEquals(0, service.removeStudentFromCourse());
    }

    @Test
    void removeStudentFromCourse_shouldShowCollectionOfCourses_whenCourseDaoReturnSetOfCourses() throws SQLException {
        Set<Course> studentCourses = new HashSet<>();
        studentCourses.add(new Course());
        Mockito.when(courseDao.getCoursesByStudentId(ArgumentMatchers.anyInt())).thenReturn(studentCourses);
        service.removeStudentFromCourse();
        Mockito.verify(view, Mockito.times(1)).showCollection(studentCourses);
    }

    @Test
    void removeStudentFromCourse_shouldShowMessageAskingCourseId_whenCourseDaoReturnSetOfCourses() throws SQLException {
        Set<Course> studentCourses = new HashSet<>();
        studentCourses.add(new Course());
        Mockito.when(courseDao.getCoursesByStudentId(ArgumentMatchers.anyInt())).thenReturn(studentCourses);
        service.removeStudentFromCourse();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.COURSE_ID);
    }

    @Test
    void removeStudentFromCourse_shouldRequestStudentDaoToRemoveStudentFromCourseWithStudentIdAndCourseIdAsParameters_whenCourseDaoReturnSetOfCourses() throws
        SQLException {
        Set<Course> studentCourses = new HashSet<>();
        studentCourses.add(new Course());
        Mockito.when(reader.askInt()).thenReturn(13).thenReturn(25);
        Mockito.when(courseDao.getCoursesByStudentId(ArgumentMatchers.anyInt())).thenReturn(studentCourses);
        service.removeStudentFromCourse();
        Mockito.verify(studentDao, Mockito.times(1)).removeStudentFromCourse(13, 25);
    }

    @Test
    void removeStudentFromCourse_shouldReturnResultOfStudentDaoMethod_whenStudentDaoMethodReturnOne() throws
        SQLException {
        Set<Course> studentCourses = new HashSet<>();
        studentCourses.add(new Course());
        Mockito.when(courseDao.getCoursesByStudentId(ArgumentMatchers.anyInt())).thenReturn(studentCourses);
        Mockito.when(studentDao.removeStudentFromCourse(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()))
               .thenReturn(1);
        Assertions.assertEquals(1, service.removeStudentFromCourse());
    }

    @Test
    void addStudentToCourse_shouldShowMessageAskingStudentId_whenRunMethod() throws SQLException {
        service.addStudentToCourse();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.STUDENT_ID);
    }

    @Test
    void addStudentToCourse_shouldFindAllCourses_whenRunMethod() throws SQLException {
        service.addStudentToCourse();
        Mockito.verify(courseDao, Mockito.times(1)).findAll();
    }

    @Test
    void addStudentToCourse_shouldShowCollectionOfCourses_whenRunMethodAndCourseDaoReturnsListOfCourses() throws
        SQLException {
        List<Course> allCourses = new ArrayList<>();
        Mockito.when(courseDao.findAll()).thenReturn(allCourses);
        service.addStudentToCourse();
        Mockito.verify(view, Mockito.times(1)).showCollection(allCourses);
    }

    @Test
    void addStudentToCourse_shouldShowMessageAskingCourseId_whenRunMethod() throws SQLException {
        service.addStudentToCourse();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.COURSE_ID);
    }

    @Test
    void addStudentToCourse_shouldRunStudentDaoAddStudentToCourse_whenUserInputStudentIdAndCourseId() throws
        SQLException {
        Mockito.when(reader.askInt()).thenReturn(13).thenReturn(25);
        service.addStudentToCourse();
        Mockito.verify(studentDao, Mockito.times(1)).addStudentToCourse(13, 25);
    }

    @Test
    void addStudentToCourse_shouldReturnResultOfStudentDaoMethod_whenStudentDaoMethodReturnOne() throws SQLException {
        Mockito.when(studentDao.addStudentToCourse(ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt())).thenReturn(1);
        Assertions.assertEquals(1, service.addStudentToCourse());
    }

    @Test
    void deleteStudentById_shouldShowMessageAskingStudentId_whenRunMethod() throws SQLException {
        service.deleteStudentById();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.STUDENT_ID);
    }

    @Test
    void deleteStudentById_shouldRunStudentDaoDeleteStudentById_whenUserInputStudentId() throws SQLException {
        Mockito.when(reader.askInt()).thenReturn(13);
        service.deleteStudentById();
        Mockito.verify(studentDao, Mockito.times(1)).deleteById(13);
    }

    @Test
    void deleteStudentById_shouldReturnResultOfStudentDaoMethod_whenStudentDaoMethodReturnOne() throws SQLException {
        Mockito.when(studentDao.deleteById(ArgumentMatchers.anyInt())).thenReturn(1);
        Assertions.assertEquals(1, service.deleteStudentById());
    }

    @Test
    void addNewStudent_shouldShowMessageAskingFirstName_whenRunMethod() throws SQLException {
        service.addNewStudent();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.FIRST_NAME);
    }

    @Test
    void addNewStudent_shouldShowMessageAskingLastName_whenRunMethod() throws SQLException {
        service.addNewStudent();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.LAST_NAME);
    }

    @Test
    void addNewStudent_shouldCreateStudentObjectSetFirstAndLastNameAndCallStudentDaoAddMethod_whenUserInputFirstAndLastName() throws
        SQLException {
        Student student = new Student.Builder().withFirstName("Bob").withLastName("Black").build();
        Mockito.when(reader.askString()).thenReturn("Bob").thenReturn("Black");
        service.addNewStudent();
        Mockito.verify(studentDao, Mockito.times(1)).addStudent(student);
    }

    @Test
    void addNewStudent_shouldReturnResultOfStudentDaoMethod_whenStudentDaoMethodReturnOne() throws SQLException {
        Mockito.when(studentDao.addStudent(new Student())).thenReturn(1);
        Assertions.assertEquals(1, service.addNewStudent());
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldCallCourseDaoFindAllMethod_whenRunMethod() throws
        SQLException {
        service.findAllStudentsRelatedToCourseWithGivenName();
        Mockito.verify(courseDao, Mockito.times(1)).findAll();
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldShowCollectionOfListOfCourses_whenCourseDaoReturnListOfCourses() throws
        SQLException {
        List<Course> allCourses = new ArrayList<>();
        Mockito.when(courseDao.findAll()).thenReturn(allCourses);
        service.findAllStudentsRelatedToCourseWithGivenName();
        Mockito.verify(view, Mockito.times(1)).showCollection(allCourses);
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldShowMessageAskingCourseId_whenRunMethod() throws
        SQLException {
        service.findAllStudentsRelatedToCourseWithGivenName();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.COURSE_ID);
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldCallCorrespondingStudentDaoMethod_whenUserInputCourseId() throws
        SQLException {
        Mockito.when(reader.askInt()).thenReturn(13);
        service.findAllStudentsRelatedToCourseWithGivenName();
        Mockito.verify(studentDao, Mockito.times(1)).getStudentsByCourseId(13);
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldReturnZero_whenStudentDaoReturnsEmptyStudentSet() throws
        SQLException {
        Mockito.when(studentDao.getStudentsByCourseId(ArgumentMatchers.anyInt())).thenReturn(new HashSet<>());
        Assertions.assertEquals(0, service.findAllStudentsRelatedToCourseWithGivenName());
    }

    @Test
    void findAllStudentsRelatedToCourseWithGivenName_shouldReturnStudentSet_whenStudentDaoReturnsStudentSet() throws
        SQLException {
        Set<Student> students = new HashSet<>();
        students.add(new Student());
        Mockito.when(studentDao.getStudentsByCourseId(ArgumentMatchers.anyInt())).thenReturn(students);
        Assertions.assertEquals(students, service.findAllStudentsRelatedToCourseWithGivenName());
    }

    @Test
    void findAllGroupsWithLessOrEqualStudentCount_shouldShowMessageAskingStudentCount_whenRunMethod() throws
        SQLException {
        service.findAllGroupsWithLessOrEqualStudentCount();
        Mockito.verify(view, Mockito.times(1)).showMessage(AppView.STUDENT_COUNT);
    }

    @Test
    void findAllGroupsWithLessOrEqualStudentCount_shouldCallCorrespondingGroupDaoMethod_whenUserInputStudentCount() throws
        SQLException {
        Mockito.when(reader.askInt()).thenReturn(13);
        service.findAllGroupsWithLessOrEqualStudentCount();
        Mockito.verify(groupDao, Mockito.times(1)).findAllGroupsWithLessOrEqualsStudCount(13);
    }

    @Test
    void findAllGroupsWithLessOrEqualStudentCount_shouldReturnZero_whenStudentDaoReturnsEmptyGroupList() throws
        SQLException {
        Mockito.when(groupDao.findAllGroupsWithLessOrEqualsStudCount(ArgumentMatchers.anyInt()))
               .thenReturn(new ArrayList<>());
        Assertions.assertEquals(0, service.findAllGroupsWithLessOrEqualStudentCount());
    }

    @Test
    void findAllGroupsWithLessOrEqualStudentCount_shouldReturnGroupList_whenStudentDaoReturnsGroupList() throws
        SQLException {
        List<Group> groups = new ArrayList<>();
        groups.add(new Group());
        Mockito.when(groupDao.findAllGroupsWithLessOrEqualsStudCount(ArgumentMatchers.anyInt())).thenReturn(groups);
        Assertions.assertEquals(groups, service.findAllGroupsWithLessOrEqualStudentCount());
    }
}
