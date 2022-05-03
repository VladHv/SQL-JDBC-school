package ua.foxminded.herasimov.task7.dao;

import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CourseDao {

    private final Connection connection;

    public CourseDao() {
        this.connection = DBConnection.getConnection();
    }

    public int addCourse(Course course) throws SQLException {
        String sql = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            throw e;
        }
        return result;
    }


}
