package ua.foxminded.herasimov.task7.dao;

import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class StudentDao {

    private Connection connection;

    {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String add(Student student) {
        String sql = "INSERT INTO students (first_name, last_name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            if (statement.executeUpdate() > 0) {
                return "Student added!";
            } else {
                return "Something went wrong";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public int updateGroupById(Integer studentId, Integer groupId) {
        String sql = "UPDATE students SET group_id = (?) WHERE student_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, groupId);
            statement.setInt(2, studentId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Student> getAll() throws SQLException {
        String sql = "SELECT * FROM students";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Student> result = new ArrayList<>();
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("student_id"));
                student.setGroupId(resultSet.getInt("group_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                result.add(student);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String addStudCourse(Integer studentId, Integer courseId) {
        String sql = "INSERT  INTO students_courses (student_id, course_id) " +
                     "SELECT student_id, course_id " +
                     "FROM students, courses " +
                     "WHERE student_id = ? " +
                     "AND course_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            int rowsAdded = statement.executeUpdate();
            if (rowsAdded > 0) {
                return "A course was added successfully!";
            } else {
                return "Something went wrong";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }


    public Set<Student> getStudentsByCourseId(Integer courseId) {
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
            e.printStackTrace();
        }
        return result;
    }

    public String deleteById(Integer studentId) {
        String sql = "DELETE FROM students WHERE student_id = (?) ";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            if (statement.executeUpdate() > 0) {
                return "Student removed!";
            } else {
                return "Student not exist!";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public String removeStudCourse(int studentId, int courseId) {
        String sql = "DELETE FROM students_courses WHERE student_id = (?) AND course_id = (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);
            int rowsAdded = statement.executeUpdate();
            if (rowsAdded > 0) {
                return "A course was removed successfully!";
            } else {
                return "Something went wrong";
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public Student findById(Integer id) {
        String sql = "SELECT * FROM students WHERE student_id = (?)";
        Student student = new Student();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    student.setId(resultSet.getInt("student_id"));
                    student.setFirstName(resultSet.getString("first_name"));
                    student.setLastName(resultSet.getString("last_name"));
                    student.setGroupId(resultSet.getInt("group_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }
}
