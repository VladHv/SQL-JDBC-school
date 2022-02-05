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

    private Connection connection;

    {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(Course course) {
        String sql = "INSERT INTO courses (name, description) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Course> getAll() {
        String sql = "SELECT * FROM courses";
        List<Course> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("course_id"));
                course.setName(resultSet.getString("name"));
                course.setDescription(resultSet.getString("description"));
                result.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Course findById(Integer id) {
        String sql = "SELECT * FROM courses WHERE course_id = (?)";
        Course course = new Course();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    course.setId(resultSet.getInt("course_id"));
                    course.setName(resultSet.getString("name"));
                    course.setDescription(resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    public Set<Course> getCoursesByStudentId(Integer studentId) {
        String sql = "SELECT course_id FROM students_courses WHERE student_id =(?)";
        Set<Course> result = new HashSet<>();
        try (PreparedStatement statement
                 = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(findById(resultSet.getInt(1)));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


}
