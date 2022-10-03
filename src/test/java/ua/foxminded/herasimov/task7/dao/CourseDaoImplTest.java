package ua.foxminded.herasimov.task7.dao;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.herasimov.task7.dao.impl.CourseDaoImpl;
import ua.foxminded.herasimov.task7.entity.Course;

import javax.sql.DataSource;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CourseDaoImplTest {

    private static EmbeddedPostgres db;

    static {
        try {
            db = EmbeddedPostgres.builder().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static DataSource dataSource = db.getPostgresDatabase();

    private CourseDaoImpl dao = new CourseDaoImpl(dataSource.getConnection());

    CourseDaoImplTest() throws SQLException {
    }

    @BeforeAll
    static void createTables() throws FileNotFoundException, URISyntaxException, SQLException {
        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
        File sqlScript = new File(GroupDaoImplTest.class.getClassLoader().getResource("create_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @AfterAll
    static void dropTables() throws URISyntaxException, FileNotFoundException, SQLException {
        ScriptRunner scriptRunner = new ScriptRunner(dataSource.getConnection());
        File sqlScript = new File(GroupDaoImplTest.class.getClassLoader().getResource("drop_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @Test
    void addCourse_shouldAddRowWithNewCourseToCoursesTable_whenAddCourseObject() throws SQLException {
        Course course = new Course.Builder().withName("Chill").withDescription("Chill is chill").build();
        dao.addCourse(course);
        assertTrue(findAllUtil().contains(course));
    }

    @Test
    void findAll_shouldReturnListOfAllCoursesFromTable_whenTableContainsCourses() throws SQLException {
        List<Course> all = dao.findAll();
        assertEquals(4, all.size());
    }

    @Test
    void findById_shouldReturnCourseByRequestedId_whenInsertExistingId() throws SQLException {
        Course course = dao.findById(1);
        Course expectedCourse = new Course.Builder().withName("Math").withDescription("Math is bad!").build();
        assertEquals(expectedCourse, course);
    }

    @Test
    void getCoursesByStudentId_shouldReturnListOfCoursesWithRequestedStudent_whenRequestExistingStudentId() throws
        SQLException {
        Set<Course> coursesByStudentId = dao.getCoursesByStudentId(1);
        assertEquals(3, coursesByStudentId.stream().findAny().get().getId());
    }

    private List<Course> findAllUtil() throws SQLException {
        String sql = "SELECT * FROM courses";
        List<Course> result = new ArrayList<>();
        Course course;
        try (PreparedStatement statement = dataSource.getConnection().prepareStatement(sql);
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
