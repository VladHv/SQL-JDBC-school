package ua.foxminded.herasimov.task7.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.herasimov.task7.dao.DBConnection;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.entity.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl implements GroupDao {

    private static final Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);
    private final Connection connection;

    public GroupDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    public GroupDaoImpl(Connection connection) {
        this.connection = connection;
    }

    public Group addGroup(Group group) throws SQLException {
        String sql = "INSERT INTO groups (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, group.getName());
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                keys.next();
                return new Group.Builder().withId(keys.getInt("group_id"))
                                          .withName(keys.getString("name"))
                                          .build();
            }
        } catch (SQLException e) {
            logger.error("Group not added to DB because of {}", e.getMessage());
            throw e;
        }
    }

    public List<Group> findAll() throws SQLException {
        String sql = "SELECT * FROM groups";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Group> result = new ArrayList<>();
            while (resultSet.next()) {
                Group group = new Group.Builder()
                    .withId(resultSet.getInt("group_id"))
                    .withName(resultSet.getString("name"))
                    .build();
                result.add(group);
            }
            return result;
        } catch (SQLException e) {
            logger.error("All groups not taken from DB because of {}", e.getMessage());
            throw e;
        }
    }

    public List<Group> findAllGroupsWithLessOrEqualsStudCount(Integer studentCount) throws SQLException {
        String sql = "SELECT groups.group_id, groups.name\n" +
                     "FROM groups\n" +
                     "INNER JOIN\n" +
                     "(SELECT group_id, COUNT(group_id) FROM students GROUP BY group_id HAVING COUNT(group_id) <= (?)" +
                     ") count_groups\n" +
                     "ON groups.group_id = count_groups.group_id;";

        List<Group> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, studentCount);
            try (ResultSet resultSet = statement.executeQuery()) {
                Group group;
                while (resultSet.next()) {
                    group = new Group.Builder()
                        .withId(resultSet.getInt("group_id"))
                        .withName(resultSet.getString("name"))
                        .build();
                    result.add(group);
                }
            }
        } catch (SQLException e) {
            logger.error("Groups with required student count not taken from DB because of {}", e.getMessage());
            throw e;
        }
        return result;
    }
}
