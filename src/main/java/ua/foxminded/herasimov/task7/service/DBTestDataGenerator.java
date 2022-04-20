package ua.foxminded.herasimov.task7.service;

import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.DataContainer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class DBTestDataGenerator {

    private final Connection connection;
    private StudentDao studentDao;
    private GroupDao groupDao;
    private CourseDao courseDao;
    private final Random rand = new Random();

    public DBTestDataGenerator(Connection connection) {
        this.connection = connection;
    }

    public void generateTestData() throws SQLException {
        studentDao = new StudentDao(connection);
        groupDao = new GroupDao(connection);
        courseDao = new CourseDao(connection);
        generateGroups();
        generateStudents();
        generateCourses();
        assignStudentsToGroups();
        assignStudentsToCourses();
    }

    private void generateCourses() throws SQLException {
        List<String> courseNames = DataContainer.getCourseNames();
        Course course;
        for (String courseName : courseNames) {
            course = new Course();
            course.setName(courseName);
            course.setDescription(courseName + " is cool!!");
            courseDao.addCourse(course);
        }
    }

    private void generateGroups() throws SQLException {
        Group group;
        for (int i = 0; i < 10; i++) {
            group = new Group();
            group.setName(randomGroupName());
            groupDao.addGroup(group);
        }
    }

    private String randomGroupName() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.valueOf(chars.charAt(rand.nextInt(chars.length()))) +
               chars.charAt(rand.nextInt(chars.length())) +
               "-" +
               (rand.nextInt(89) + 10);
    }

    private void generateStudents() throws SQLException {
        List<String> studentFirstNames = DataContainer.getStudentFirstNames();
        List<String> studentLastNames = DataContainer.getStudentLastNames();

        Student student;
        for (int i = 0; i < 200; i++) {
            student = new Student();
            student.setFirstName(studentFirstNames.get(rand.nextInt(studentFirstNames.size())));
            student.setLastName((studentLastNames.get(rand.nextInt(studentLastNames.size()))));
            studentDao.add(student);
        }
    }

    private void assignStudentsToGroups() throws SQLException {
        int studentCount = 0;
        List<Group> groupsFromDB = null;
        List<Student> studentsFromDB = null;
        try {
            groupsFromDB = groupDao.findAll();
            studentsFromDB = studentDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Group gr : groupsFromDB) {
            for (int i = 0; i < (rand.nextInt(21) + 10); i++) {
                studentDao.updateGroupByStudentId(studentsFromDB.get(studentCount).getId(), gr.getId());
                studentCount++;
            }
        }
    }

    private void assignStudentsToCourses() throws SQLException {

        List<Course> coursesFromDB = null;
        List<Student> studentsFromDB = null;

        try {
            coursesFromDB = courseDao.findAll();
            studentsFromDB = studentDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Student student : studentsFromDB) {
            for (int i = 0; i < (rand.nextInt(3) + 1); i++) {
                int randomCourseIndex = rand.nextInt(coursesFromDB.size());
                studentDao.addStudentToCourse(student.getId(), coursesFromDB.get(randomCourseIndex).getId());
            }
        }

    }

}
