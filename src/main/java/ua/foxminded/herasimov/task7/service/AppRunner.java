package ua.foxminded.herasimov.task7.service;

import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.view.AppView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AppRunner {

    private final Connection connection;
    private StudentDao studentDao;
    private GroupDao groupDao;
    private CourseDao courseDao;
    private final AppView view = new AppView();
    private final Scanner scanner = new Scanner(System.in);

    public AppRunner(Connection connection) {
        this.connection = connection;
    }

    public void startApp() throws SQLException {
        studentDao = new StudentDao(connection);
        groupDao = new GroupDao(connection);
        courseDao = new CourseDao(connection);

        view.showMessage(AppView.MAIN_MENU);
        int result = chooseFunction();
        view.showActionResult(result);
        if (result >= 0) {
            startApp();
        }
    }

    private int chooseFunction() throws SQLException {
        view.showMessage(AppView.CHOOSE);
        int functionNumber = scanner.nextInt();
        switch (functionNumber) {
            case 1:
                return findAllGroupsWithLessOrEqualStudentCount();
            case 2:
                return findAllStudentsRelatedToCourseWithGivenName();
            case 3:
                return addNewStudent();
            case 4:
                return deleteStudentById();
            case 5:
                return addStudentToCourse();
            case 6:
                return removeStudentFromCourse();
            default:
                return -1;
        }
    }

    private int removeStudentFromCourse() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = scanner.nextInt();
        Set<Course> studentCourses = courseDao.getCoursesByStudentId(studentId);
        if (studentCourses.isEmpty()) {
            return 0;
        } else {
            view.showCourseList(studentCourses);
            view.showMessage(AppView.COURSE_ID);
            int courseId = scanner.nextInt();
            return studentDao.removeStudentFromCourse(studentId, courseId);
        }
    }

    private int addStudentToCourse() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = scanner.nextInt();
        List<Course> allCourses = courseDao.findAll();
        view.showCourseList(allCourses);
        view.showMessage(AppView.COURSE_ID);
        int courseId = scanner.nextInt();
        return studentDao.addStudentToCourse(studentId, courseId);
    }

    private int deleteStudentById() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = scanner.nextInt();
        return studentDao.deleteById(studentId);
    }

    private int addNewStudent() throws SQLException {
        Student student = new Student();
        view.showMessage(AppView.FIRST_NAME);
        String firstName = scanner.next();
        view.showMessage(AppView.LAST_NAME);
        String lastName = scanner.next();

        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDao.add(student);
    }

    private int findAllStudentsRelatedToCourseWithGivenName() throws SQLException {
        List<Course> allCourses = courseDao.findAll();
        view.showCourseList(allCourses);
        view.showMessage(AppView.COURSE_ID);
        int courseId = scanner.nextInt();
        Set<Student> students = studentDao.getStudentsByCourseId(courseId);
        if (students.isEmpty()) {
            return 0;
        } else {
            view.showStudentList(students);
            return 1;
        }
    }

    private int findAllGroupsWithLessOrEqualStudentCount() throws SQLException {
        view.showMessage(AppView.STUDENT_COUNT);
        int studentCount = scanner.nextInt();
        List<Group> groups = groupDao.findAllGroupsWithLessOrEqualsStudCount(studentCount);
        if (groups.isEmpty()) {
            return 0;
        } else {
            view.showGroupList(groups);
            return 1;
        }
    }
}
