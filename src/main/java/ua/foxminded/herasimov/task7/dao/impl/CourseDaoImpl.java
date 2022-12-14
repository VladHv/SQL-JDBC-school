package ua.foxminded.herasimov.task7.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.DBConnection;
import ua.foxminded.herasimov.task7.entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseDaoImpl implements CourseDao {

    private static final Logger logger = LoggerFactory.getLogger(CourseDaoImpl.class);
    private final Connection connection;

    public CourseDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    public CourseDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Course addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return new Course.Builder().withId(keys.getInt("course_id"))
                                           .withName(keys.getString("name"))
                                           .withDescription(keys.getString("description"))
                                           .build();
            }
        } catch (SQLException e) {
            logger.error("Course not added to DB because of {}", e.getMessage());
            throw e;
        }
    }

    public List<Course> findAll() throws SQLException {
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
            logger.error("All courses not taken from DB because of {}", e.getMessage());
            throw e;
        }
        return result;
    }

    public Course findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_id = (?)";
        Course course = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    course = new Course.Builder()
                        .withId(resultSet.getInt("course_id"))
                        .withName(resultSet.getString("name"))
                        .withDescription(resultSet.getString("description"))
                        .build();
                }
            }
        } catch (SQLException e) {
            logger.error("Course not taken from DB because of {}", e.getMessage());
            throw e;
        }
        return course;
    }

    public Set<Course> getCoursesByStudentId(Integer studentId) throws SQLException {
        String sql = "SELECT course_id FROM students_courses WHERE student_id =(?)";
        Set<Course> result = new HashSet<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(findById(resultSet.getInt(1)));
                }
            }
        } catch (SQLException e) {
            logger.error("Courses by Student ID not taken from DB because of {}", e.getMessage());
            throw e;
        }
        return result;
    }


}
