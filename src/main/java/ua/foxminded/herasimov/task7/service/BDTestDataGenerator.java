package ua.foxminded.herasimov.task7.service;

import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.DataContainer;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class BDTestDataGenerator {

    private final GroupDao groupDao = new GroupDao();
    private final StudentDao studentDao = new StudentDao();
    private final CourseDao courseDao = new CourseDao();
    private DataContainer container = new DataContainer();
    private final Random rand = new Random();

    public void generateTestData() {
        container.initData();
        generateGroups();
        generateStudents();
        generateCourses();
        assignStudentsToGroups();
    }

    private void generateCourses() {
        List<String> courseNames = container.getCourseNames();
        Course course;
        for (String courseName : courseNames) {
            course = new Course();
            course.setName(courseName);
            course.setDescription(courseName + " is cool!!");
            courseDao.add(course);
        }
    }

    private void generateGroups() {
        Group group;
        for (int i = 0; i < 10; i++) {
            group = new Group();
            group.setName(randomGroupName());
            groupDao.add(group);
        }
    }

    private String randomGroupName() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return String.valueOf(chars.charAt(rand.nextInt(chars.length()))) +
               chars.charAt(rand.nextInt(chars.length())) +
               "-" +
               (rand.nextInt(89) + 10);
    }

    private void generateStudents() {
        List<String> studentFirstNames = container.getStudentFirstNames();
        List<String> studentLastNames = container.getStudentLastNames();

        Student student;
        for (int i = 0; i < 200; i++) {
            student = new Student();
            student.setFirstName(studentFirstNames.get(rand.nextInt(studentFirstNames.size())));
            student.setLastName((studentLastNames.get(rand.nextInt(studentLastNames.size()))));
            studentDao.add(student);
        }
    }

    private void assignStudentsToGroups() {

        int studentCount = 0;
        List<Group> groupsFromDB = null;
        List<Student> studentsFromDB = null;
        try {
            groupsFromDB = groupDao.getAll();
            studentsFromDB = studentDao.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Group gr : groupsFromDB) {
            for (int i = 0; i < (rand.nextInt(21) + 10); i++) {
                studentDao.updateGroupById(studentsFromDB.get(studentCount).getId(), gr.getId());
                studentCount++;
            }
        }
    }

}
