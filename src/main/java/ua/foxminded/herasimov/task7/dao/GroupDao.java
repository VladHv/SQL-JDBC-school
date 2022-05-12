package ua.foxminded.herasimov.task7.dao;

import ua.foxminded.herasimov.task7.entity.Group;

import java.sql.SQLException;
import java.util.List;

public interface GroupDao {
    int addGroup(Group group) throws SQLException;

    List<Group> findAll() throws SQLException;

    List<Group> findAllGroupsWithLessOrEqualsStudCount(Integer studentCount) throws SQLException;
}
