package ua.foxminded.herasimov.task7.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {

    private static final Logger LOG = LoggerFactory.getLogger(GroupDao.class);
    private final Connection connection;

    public GroupDao() {
        this.connection = DBConnection.getConnection();
    }

    public int addGroup(Group group) throws SQLException {
        String sql = "INSERT INTO groups (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, group.getName());
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOG.debug("Group not added to DB because of {}", e.getMessage());
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
            LOG.debug("All groups not taken from DB because of {}", e.getMessage());
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
            LOG.debug("Groups with required student count not taken from DB because of {}", e.getMessage());
            throw e;
        }
        return result;
    }
}
