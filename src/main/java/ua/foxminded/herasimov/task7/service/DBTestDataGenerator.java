package ua.foxminded.herasimov.task7.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.herasimov.task7.dao.impl.CourseDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.GroupDaoImpl;
import ua.foxminded.herasimov.task7.dao.impl.StudentDaoImpl;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.DataContainer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBTestDataGenerator {

    private final Logger LOG = LoggerFactory.getLogger(DBTestDataGenerator.class);
    private final StudentDaoImpl studentDao;
    private final GroupDaoImpl groupDao;
    private final CourseDaoImpl courseDao;
    private final DataContainer container;
    private final Random rand;

    private final List<Course> generatedCourses = new ArrayList<>();
    private final List<Student> generatedStudents = new ArrayList<>();
    private final List<Group> generatedGroups = new ArrayList<>();

    public DBTestDataGenerator(StudentDaoImpl studentDao, GroupDaoImpl groupDao,
                               CourseDaoImpl courseDao, DataContainer container, Random rand) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.container = container;
        this.rand = rand;
    }

    public void generateTestData() throws SQLException {
        generateGroups();
        generateStudents();
        generateCourses();
        assignStudentsToGroups();
        assignStudentsToCourses();
    }


    private void generateCourses() throws SQLException {
        List<String> courseNames = container.getCourseNames();
        Course course;
        for (String courseName : courseNames) {
            course = new Course();
            course.setName(courseName);
            course.setDescription(courseName + " is cool!!");
            generatedCourses.add(course);
            courseDao.addCourse(course);
        }
    }

    private void generateGroups() throws SQLException {
        Group group;
        for (int i = 0; i < 10; i++) {
            group = new Group();
            group.setName(randomGroupName());
            generatedGroups.add(group);
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
        List<String> studentFirstNames = container.getStudentFirstNames();
        List<String> studentLastNames = container.getStudentLastNames();

        Student student;
        for (int i = 0; i < 200; i++) {
            student = new Student();
            student.setFirstName(studentFirstNames.get(rand.nextInt(studentFirstNames.size())));
            student.setLastName((studentLastNames.get(rand.nextInt(studentLastNames.size()))));
            generatedStudents.add(student);
            studentDao.addStudent(student);
        }
    }

    private void assignStudentsToGroups() throws SQLException {
        List<Group> groupsFromDB = null;
        List<Student> studentsFromDB = null;
        try {
            groupsFromDB = groupDao.findAll();
            studentsFromDB = studentDao.findAll();
        } catch (SQLException e) {
            LOG.debug("All groups and/or students list(s) not received because of {}", e.getMessage());
        }

        int studentCount = 0;
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

    public List<Course> getGeneratedCourses() {
        return generatedCourses;
    }

    public List<Student> getGeneratedStudents() {
        return generatedStudents;
    }

    public List<Group> getGeneratedGroups() {
        return generatedGroups;
    }
}
