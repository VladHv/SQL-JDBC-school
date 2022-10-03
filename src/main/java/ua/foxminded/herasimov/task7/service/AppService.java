package ua.foxminded.herasimov.task7.service;

import ua.foxminded.herasimov.task7.dao.impl.CourseDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.GroupDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.StudentDaoImpl;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.Reader;
import ua.foxminded.herasimov.task7.view.AppView;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class AppService {

    private final AppView view;
    private final Reader reader;
    private final CourseDaoImpl courseDao;
    private final StudentDaoImpl studentDao;
    private final GroupDaoImpl groupDao;

    public AppService(AppView view, Reader reader, CourseDaoImpl courseDao,
                      StudentDaoImpl studentDao, GroupDaoImpl groupDao) {
        this.view = view;
        this.reader = reader;
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.groupDao = groupDao;
    }

    public int removeStudentFromCourse() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = reader.readInt();
        Set<Course> studentCourses = courseDao.getCoursesByStudentId(studentId);
        if (studentCourses.isEmpty()) {
            return 0;
        } else {
            view.showCollection(studentCourses);
            view.showMessage(AppView.COURSE_ID);
            int courseId = reader.readInt();
            return studentDao.removeStudentFromCourse(studentId, courseId);
        }
    }

    public int addStudentToCourse() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = reader.readInt();
        List<Course> allCourses = courseDao.findAll();
        view.showCollection(allCourses);
        view.showMessage(AppView.COURSE_ID);
        int courseId = reader.readInt();
        return studentDao.addStudentToCourse(studentId, courseId);
    }

    public int deleteStudentById() throws SQLException {
        view.showMessage(AppView.STUDENT_ID);
        int studentId = reader.readInt();
        return studentDao.deleteById(studentId);
    }

    public Object addNewStudent() throws SQLException {
        Student student = new Student();
        view.showMessage(AppView.FIRST_NAME);
        String firstName = reader.readString();
        view.showMessage(AppView.LAST_NAME);
        String lastName = reader.readString();
        student.setFirstName(firstName);
        student.setLastName(lastName);
        return studentDao.addStudent(student);
    }

    public Object findAllStudentsRelatedToCourseWithGivenName() throws SQLException {
        List<Course> allCourses = courseDao.findAll();
        view.showCollection(allCourses);
        view.showMessage(AppView.COURSE_ID);
        int courseId = reader.readInt();
        Set<Student> students = studentDao.getStudentsByCourseId(courseId);
        if (students.isEmpty()) {
            return 0;
        } else {
            return students;
        }
    }

    public Object findAllGroupsWithLessOrEqualStudentCount() throws SQLException {
        view.showMessage(AppView.STUDENT_COUNT);
        int studentCount = reader.readInt();
        List<Group> groups = groupDao.findAllGroupsWithLessOrEqualsStudCount(studentCount);
        if (groups.isEmpty()) {
            return 0;
        } else {
            return groups;
        }
    }
}
