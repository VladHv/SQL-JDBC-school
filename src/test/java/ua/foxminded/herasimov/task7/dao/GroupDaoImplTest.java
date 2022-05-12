package ua.foxminded.herasimov.task7.dao;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.foxminded.herasimov.task7.dao.impl.GroupDaoImpl;
import ua.foxminded.herasimov.task7.entity.Group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupDaoImplTest {

    GroupDaoImpl dao = new GroupDaoImpl();

    @BeforeAll
    static void createTables() throws FileNotFoundException, URISyntaxException {
        ScriptRunner scriptRunner = new ScriptRunner(new DBConnection().getConnection());
        File sqlScript = new File(GroupDaoImplTest.class.getClassLoader().getResource("create_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @AfterAll
    static void dropTables() throws URISyntaxException, FileNotFoundException {
        ScriptRunner scriptRunner = new ScriptRunner(new DBConnection().getConnection());
        File sqlScript = new File(GroupDaoImplTest.class.getClassLoader().getResource("drop_tables.sql").toURI());
        BufferedReader reader = new BufferedReader(new FileReader(sqlScript));
        scriptRunner.runScript(reader);
    }

    @Test
    void addGroup_shouldAddRowWithNewGroupToGroupsTable_whenAddGroupObject() throws SQLException {
        Group group = new Group.Builder().withName("XX-00").build();
        dao.addGroup(group);
        assertTrue(findAllUtil().contains(group));
    }

    @Test
    void findAll_shouldReturnListOfAllGroupsFromTable_whenTableContainsGroups() throws SQLException {
        List<Group> all = dao.findAll();
        assertEquals(3, all.size());
    }

    @Test
    void findAllGroupsWithLessOrEqualsStudCount_shouldReturnListWithGroupsThatContainRequestedNumberOfStudents_whenInsertExistingNumberOfReferences() throws
        SQLException {
        List<Group> actual = dao.findAllGroupsWithLessOrEqualsStudCount(2);
        Group expected = new Group.Builder().withName("AA-11").build();
        assertEquals(expected, actual.stream().findAny().get());
    }

    public List<Group> findAllUtil() throws SQLException {
        String sql = "SELECT * FROM groups";
        try (PreparedStatement statement = DBConnection.getConnection().prepareStatement(sql);
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

}
