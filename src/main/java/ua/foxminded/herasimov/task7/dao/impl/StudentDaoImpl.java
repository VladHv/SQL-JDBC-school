package ua.foxminded.herasimov.task7.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.herasimov.task7.dao.DBConnection;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StudentDaoImpl implements StudentDao {

    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);
    private final Connection connection;

    public StudentDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    public StudentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Object addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return new Student.Builder().withId(keys.getInt("student_id"))
                                            .withFirstName(keys.getString("first_name"))
                                            .withLastName(keys.getString("last_name"))
                                            .build();
            }
        } catch (SQLException e) {
            logger.error("Student not added to DB because of {}", e.getMessage());
            throw e;
        }
    }

    public int updateGroupByStudentId(Integer studentId, Integer groupId) throws SQLException {
        String sql = "UPDATE students SET group_id = (?) WHERE student_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, groupId);
            statement.setInt(2, studentId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Group not updated with student id in DB because of {}", e.getMessage());
            throw e;
        }
    }

    public List<Student> findAll() throws SQLException {
        String sql = "SELECT * FROM students";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Student> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new Student.Builder()
                               .withId(resultSet.getInt("student_id"))
                               .withGroupId(resultSet.getInt("group_id"))
                               .withFirstName(resultSet.getString("first_name"))
                               .withLastName(resultSet.getString("last_name"))
                               .build());
            }
            return result;
        } catch (SQLException e) {
            logger.error("All student not taken from DB because of {}", e.getMessage());
            throw e;
        }
    }

    public int addStudentToCourse(Integer studentId, Integer courseId) throws SQLException {
        String sql = "INSERT  INTO students_courses (student_id, course_id) " +
                     "SELECT student_id, course_id " +
                     "FROM students, courses " +
                     "WHERE student_id = ? " +
                     "AND course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Student not added to course in DB because of {}", e.getMessage());
            throw e;
        }
    }


    public Set<Student> getStudentsByCourseId(Integer courseId) throws SQLException {
        String sql = "SELECT student_id FROM students_courses WHERE course_id =(?) ORDER BY student_id ASC";
        Set<Student> result = new LinkedHashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, courseId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(findById(resultSet.getInt(1)));
                }
            }
        } catch (SQLException e) {
            logger.error("Student not taken from DB by course id because of {}", e.getMessage());
            throw e;
        }
        return result;
    }

    public int deleteById(Integer studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = (?) ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Student not removed from DB because of {}", e.getMessage());
            throw e;
        }
    }

    public int removeStudentFromCourse(int studentId, int courseId) throws SQLException {
        String sql = "DELETE FROM students_courses WHERE student_id = (?) AND course_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Student not removed from course in DB because of {}", e.getMessage());
            throw e;
        }
    }

    public Student findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = (?)";
        Student student = new Student();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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
            logger.error("Student not taken from DB because of {}", e.getMessage());
            throw e;
        }
        return student;
    }
}
