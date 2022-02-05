package ua.foxminded.herasimov.task7.dao;

import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {

    private Connection connection;

    {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int add(Group group) {

        String sql = "INSERT INTO groups (name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, group.getName());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Group> getAll() throws SQLException {

        String sql = "SELECT * FROM groups";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            List<Group> result = new ArrayList<>();
            Group group;
            while (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("name"));
                result.add(group);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Group> findAllGroupsWithLessEqualStudCount(Integer studentCount) {

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
                    group = new Group();
                    group.setId(resultSet.getInt("group_id"));
                    group.setName(resultSet.getString("name"));
                    result.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
