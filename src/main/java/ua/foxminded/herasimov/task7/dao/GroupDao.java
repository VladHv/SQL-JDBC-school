package ua.foxminded.herasimov.task7.dao;

import ua.foxminded.herasimov.task7.entity.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {

    private final Connection connection;

    public GroupDao(Connection connection) {
        this.connection = connection;
    }

    public int addGroup(Group group) throws SQLException {
        String sql = "INSERT INTO groups (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, group.getName());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            throw e;
        }
        return result;
    }
}
