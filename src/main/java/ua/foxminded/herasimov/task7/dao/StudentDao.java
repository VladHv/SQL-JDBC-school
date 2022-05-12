package ua.foxminded.herasimov.task7.dao;

import ua.foxminded.herasimov.task7.entity.Student;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface StudentDao {
    int addStudent(Student student) throws SQLException;

    int updateGroupByStudentId(Integer studentId, Integer groupId) throws SQLException;

    List<Student> findAll() throws SQLException;

    int addStudentToCourse(Integer studentId, Integer courseId) throws SQLException;


    Set<Student> getStudentsByCourseId(Integer courseId) throws SQLException;

    int deleteById(Integer studentId) throws SQLException;

    int removeStudentFromCourse(int studentId, int courseId) throws SQLException;

    Student findById(Integer id) throws SQLException;
}
