package ua.foxminded.herasimov.task7.service;

import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.view.AppView;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AppRunner {

    private final StudentDao studentDao = new StudentDao();
    private final GroupDao groupDao = new GroupDao();
    private final CourseDao courseDao = new CourseDao();
    private final AppView view = new AppView();
    private final Scanner scanner = new Scanner(System.in);

    public void startApp() {
        view.showMessage(AppView.MAIN_MENU);
        menuChoose();
    }

    private void menuChoose() {
        view.showMessage(AppView.CHOOSE);
        int functionNumber = scanner.nextInt();
        switch (functionNumber) {
            case 1:
                findAllGroupsWithLessOrEqualStudentCount();
                break;
            case 2:
                findAllStudentsRelatedToCourseWithGivenName();
                break;
            case 3:
                addNewStudent();
                break;
            case 4:
                deleteStudentById();
                break;
            case 5:
                addStudentToCourse();
                break;
            case 6:
                removeStudentFromCourse();
                break;
            default:
                break;
        }

    }

    private void removeStudentFromCourse() {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = scanner.nextInt();
        Set<Course> studentCourses = courseDao.getCoursesByStudentId(studentId);
        if (studentCourses.isEmpty()) {
            view.showMessage(AppView.EMPTY);
        } else {
            for (Course course : studentCourses) {
                view.showMessage(course.getId().toString() + " " + course.getName());
            }
            view.showMessage(AppView.COURSE_ID);
            int courseId = scanner.nextInt();
            String message = studentDao.removeStudCourse(studentId, courseId);
            view.showMessage(message);
        }
        menuChoose();
    }

    private void addStudentToCourse() {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = scanner.nextInt();
        List<Course> allCourses = courseDao.getAll();
        for (Course course : allCourses) {
            view.showMessage(course.getId().toString() + " " + course.getName());
        }
        view.showMessage(AppView.COURSE_ID);
        int courseId = scanner.nextInt();
        String message = studentDao.addStudCourse(studentId, courseId);
        view.showMessage(message);
        menuChoose();
    }

    private void deleteStudentById() {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = scanner.nextInt();
        String message = studentDao.deleteById(studentId);
        view.showMessage(message);
        menuChoose();
    }

    private void addNewStudent() {
        Student student = new Student();
        view.showMessage(AppView.FIRST_NAME);
        String firstName = scanner.next();
        view.showMessage(AppView.LAST_NAME);
        String lastName = scanner.next();

        student.setFirstName(firstName);
        student.setLastName(lastName);
        String message = studentDao.add(student);
        view.showMessage(message);
        menuChoose();
    }

    private void findAllStudentsRelatedToCourseWithGivenName() {
        List<Course> allCourses = courseDao.getAll();
        for (Course course : allCourses) {
            view.showMessage(course.getId().toString() + " " + course.getName());
        }
        view.showMessage(AppView.COURSE_ID);
        int courseId = scanner.nextInt();
        Set<Student> students = studentDao.getStudentsByCourseId(courseId);
        for (Student student : students) {
            view.showMessage(student.getId().toString() + " " +
                             student.getFirstName() + " " +
                             student.getLastName());
        }
        menuChoose();

    }

    private void findAllGroupsWithLessOrEqualStudentCount() {
        view.showMessage(AppView.STUDENT_COUNT);
        int studentCount = scanner.nextInt();
        List<Group> groups = groupDao.findAllGroupsWithLessEqualStudCount(studentCount);
        for (Group group : groups) {
            view.showMessage(group.getId().toString() + " " +
                             group.getName());
        }
        menuChoose();

    }
}
