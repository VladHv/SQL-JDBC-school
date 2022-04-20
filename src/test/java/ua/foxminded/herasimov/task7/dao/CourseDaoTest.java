package ua.foxminded.herasimov.task7.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.util.DBConnection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class CourseDaoTest {

    Connection connection = DBConnection.getConnection();
    CourseDao dao = new CourseDao(connection);

    @BeforeAll
    static void createTables() throws FileNotFoundException, URISyntaxException {
        ScriptRunner scriptRunner = new ScriptRunner(new DBConnection().getConnection());
        File sqlScript = new File(GroupDaoTest.class.getClassLoader().getResource("create_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @AfterAll
    static void dropTables() throws URISyntaxException, FileNotFoundException {
        ScriptRunner scriptRunner = new ScriptRunner(new DBConnection().getConnection());
        File sqlScript = new File(GroupDaoTest.class.getClassLoader().getResource("drop_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @Test
    void addCourse_shouldAddRowWithNewCourseToCoursesTable_whenAddCourseObject() throws SQLException {
        Course course = new Course.Builder().withName("Chill").withDescription("Chill is chill").build();
        dao.addCourse(course);
        Assertions.assertTrue(findAllUtil().contains(course));
    }

    @Test
    void findAll_shouldReturnListOfAllCoursesFromTable_whenTableContainsCourses() throws SQLException {
        List<Course> all = dao.findAll();
        Assertions.assertEquals(4, all.size());
    }

    @Test
    void findById_shouldReturnCourseByRequestedId_whenInsertExistingId() throws SQLException {
        Course course = dao.findById(1);
        Course expectedCourse = new Course.Builder().withName("Math").withDescription("Math is bad!").build();
        Assertions.assertEquals(expectedCourse, course);
    }

    @Test
    void getCoursesByStudentId_shouldReturnListOfCoursesWithRequestedStudent_whenRequestExistingStudentId() throws
        SQLException {
        Set<Course> coursesByStudentId = dao.getCoursesByStudentId(1);
        Assertions.assertEquals(3, coursesByStudentId.stream().findAny().get().getId());
    }

    private List<Course> findAllUtil() throws SQLException {
        String sql = "SELECT * FROM courses";
        List<Course> result = new ArrayList<>();
        Course course;
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                course = new Course.Builder()
                    .withId(resultSet.getInt("course_id"))
                    .withName(resultSet.getString("name"))
                    .withDescription(resultSet.getString("description"))
                    .build();
                result.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return result;
    }
}
