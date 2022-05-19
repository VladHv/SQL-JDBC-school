package ua.foxminded.herasimov.task7.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.herasimov.task7.dao.impl.StudentDaoImpl;
import ua.foxminded.herasimov.task7.entity.Student;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StudentDaoImplTest {

    StudentDaoImpl dao = new StudentDaoImpl();

    @BeforeAll
    static void createTables() throws FileNotFoundException, URISyntaxException {
        ScriptRunner scriptRunner = new ScriptRunner(new DBConnection().getConnection());
        File sqlScript = new File(GroupDaoImplTest.class.getClassLoader().getResource("create_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @AfterAll
    static void dropTables() throws URISyntaxException, FileNotFoundException {
        ScriptRunner scriptRunner = new ScriptRunner(new DBConnection().getConnection());
        File sqlScript = new File(GroupDaoImplTest.class.getClassLoader().getResource("drop_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @Test
    void addStudent_shouldInsertNewStudentToStudentsTable_whenAddStudentObject() throws SQLException {
        Student student = new Student.Builder().withFirstName("Jeff").withLastName("Eddy").build();
        dao.addStudent(student);
        assertEquals(4, findAllUtil().size());
    }

    @Test
    void updateGroupByStudentId_shouldUpdateGroupIdForAppropriateStudentInTable_whenUpdate() throws SQLException {
        dao.updateGroupByStudentId(1, 3);
        assertEquals(3, findByIdUtil(1).getGroupId());
    }

    @Test
    void findAll_shouldReturnListOfAllStudentsFromTable_whenTableContainsStudents() throws SQLException {
        List<Student> list = dao.findAll();
        assertEquals(3, list.size());
    }

    @Test
    void addStudentToCourse_shouldAddNewRowWithStudentIdAndCourseId_whenInsertExistingIds() throws SQLException {
        dao.addStudentToCourse(2, 3);
        Map<Integer, Integer> studentsIdWithCoursesId = getStudentsIdWithCoursesIdUtil();
        assertEquals(3, studentsIdWithCoursesId.get(2));
    }

    @Test
    void getStudentsByCourseId_shouldReturnListOfStudentsWithRequestedCourse_whenRequestExistingCourseId() throws
        SQLException {
        Set<Student> students = dao.getStudentsByCourseId(1);
        assertEquals(3, students.stream().findAny().get().getId());
    }

    @Test
    void deleteById_shouldRemoveStudentFromTableWithAppropriateId_whenInsertExistingId() throws SQLException {
        dao.deleteById(2);
        List<Student> all = findAllUtil();
        Student student =
            new Student.Builder().withId(2).withFirstName("Anna").withLastName("Black").withGroupId(2).build();
        assertFalse(all.contains(student));
    }

    @Test
    void removeStudentFromCourse_shouldRemoveRowWithAppropriateStudentAndCourseIds_whenInsertExistingIds() throws
        SQLException {
        dao.removeStudentFromCourse(1, 3);
        Map<Integer, Integer> studentsIdWithCoursesId = getStudentsIdWithCoursesIdUtil();
        assertNull(studentsIdWithCoursesId.get(1));
    }

    @Test
    void findById_shouldReturnStudentByRequestedId_whenInsertExistingId() throws SQLException {
        Student student = dao.findById(3);
        Student expectedStudent =
            new Student.Builder().withId(3).withFirstName("Bob").withLastName("Dawn").withGroupId(1).build();
        assertEquals(expectedStudent, student);
    }

    private Map<Integer, Integer> getStudentsIdWithCoursesIdUtil() throws SQLException {
        String sql = "SELECT * FROM students_courses";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            Map<Integer, Integer> studentAndCourse = new HashMap<>();
            while (resultSet.next()) {
                studentAndCourse.put(resultSet.getInt("student_id"), resultSet.getInt("course_id"));
            }
            return studentAndCourse;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private Student findByIdUtil(Integer id) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = (?)";
        Student student = new Student();
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    student = new Student.Builder()
                        .withId(resultSet.getInt("student_id"))
                        .withFirstName(resultSet.getString("first_name"))
                        .withLastName(resultSet.getString("last_name"))
                        .withGroupId(resultSet.getInt("group_id"))
                        .build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return student;
    }

    private List<Student> findAllUtil() throws SQLException {
        String sql = "SELECT * FROM students";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Student> result = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student.Builder()
                    .withId(resultSet.getInt("student_id"))
                    .withGroupId(resultSet.getInt("group_id"))
                    .withFirstName(resultSet.getString("first_name"))
                    .withLastName(resultSet.getString("last_name"))
                    .build();
                result.add(student);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
