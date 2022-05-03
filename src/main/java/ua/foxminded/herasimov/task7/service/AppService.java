package ua.foxminded.herasimov.task7.service;

import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.UserAsker;
import ua.foxminded.herasimov.task7.view.AppView;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class AppService {

    private final AppView view;
    private final UserAsker asker;
    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final GroupDao groupDao;

    public AppService(AppView view, UserAsker asker, CourseDao courseDao,
                      StudentDao studentDao, GroupDao groupDao) {
        this.view = view;
        this.asker = asker;
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    public int removeStudentFromCourse() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = asker.askInt();
        Set<Course> studentCourses = courseDao.getCoursesByStudentId(studentId);
        if (studentCourses.isEmpty()) {
            return 0;
        } else {
            view.showCollection(studentCourses);
            view.showMessage(AppView.COURSE_ID);
            int courseId = asker.askInt();
            return studentDao.removeStudentFromCourse(studentId, courseId);
        }
    }

    public int addStudentToCourse() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = asker.askInt();
        List<Course> allCourses = courseDao.findAll();
        view.showCollection(allCourses);
        view.showMessage(AppView.COURSE_ID);
        int courseId = asker.askInt();
        return studentDao.addStudentToCourse(studentId, courseId);
    }

    public int deleteStudentById() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = asker.askInt();
        return studentDao.deleteById(studentId);
    }

    public int addNewStudent() throws SQLException {
        Student student = new Student();
        view.showMessage(AppView.FIRST_NAME);
        String firstName = asker.askString();
        view.showMessage(AppView.LAST_NAME);
        String lastName = asker.askString();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDao.addStudent(student);
    }

    public Object findAllStudentsRelatedToCourseWithGivenName() throws SQLException {
        List<Course> allCourses = courseDao.findAll();
        view.showCollection(allCourses);
        view.showMessage(AppView.COURSE_ID);
        int courseId = asker.askInt();
        Set<Student> students = studentDao.getStudentsByCourseId(courseId);
        if (students.isEmpty()) {
            return 0;
        } else {
            return students;
        }
    }

    public Object findAllGroupsWithLessOrEqualStudentCount() throws SQLException {
        view.showMessage(AppView.STUDENT_COUNT);
        int studentCount = asker.askInt();
        List<Group> groups = groupDao.findAllGroupsWithLessOrEqualsStudCount(studentCount);
        if (groups.isEmpty()) {
            return 0;
        } else {
            return groups;
        }
    }
}
