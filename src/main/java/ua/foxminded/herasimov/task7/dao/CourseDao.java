package ua.foxminded.herasimov.task7.dao;

import ua.foxminded.herasimov.task7.entity.Course;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface CourseDao {
    int addCourse(Course course) throws SQLException;

    List<Course> findAll() throws SQLException;

    Course findById(Integer id) throws SQLException;

    Set<Course> getCoursesByStudentId(Integer studentId) throws SQLException;
}
