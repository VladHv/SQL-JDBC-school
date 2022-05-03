package ua.foxminded.herasimov.task7.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import ua.foxminded.herasimov.task7.dao.CourseDao;
import ua.foxminded.herasimov.task7.dao.GroupDao;
import ua.foxminded.herasimov.task7.dao.StudentDao;
import ua.foxminded.herasimov.task7.entity.Course;
import ua.foxminded.herasimov.task7.entity.Group;
import ua.foxminded.herasimov.task7.entity.Student;
import ua.foxminded.herasimov.task7.util.DataContainer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class DBTestDataGeneratorTest {

    static StudentDao studentDao = Mockito.mock(StudentDao.class);
    static GroupDao groupDao = Mockito.mock(GroupDao.class);
    static CourseDao courseDao = Mockito.mock(CourseDao.class);
    static DataContainer container = Mockito.mock(DataContainer.class);
    static Random rand = Mockito.mock(Random.class);

    static DBTestDataGenerator dataGenerator = new DBTestDataGenerator(studentDao, groupDao, courseDao, container,
                                                                       rand);

    static List<String> courseNames;
    static List<String> studentFirstNames;
    static List<String> studentLastNames;

    static List<Course> expectedCourses = new ArrayList<>();
    static List<Student> expectedStudents = new ArrayList<>();
    static List<Group> groupsFromDB = new ArrayList<>();
    static List<Student> studentsFromDB = new ArrayList<>();
    static List<Course> coursesFromDB = new ArrayList<>();

    @BeforeAll
    static void initData() throws SQLException {
        courseNames = new ArrayList<>();
        courseNames.add("Math");
        courseNames.add("Business");
        Mockito.when(container.getCourseNames()).thenReturn(courseNames);

        studentFirstNames = new ArrayList<>();
        studentFirstNames.add("Bob");
        studentFirstNames.add("Jen");
        Mockito.when(container.getStudentFirstNames()).thenReturn(studentFirstNames);

        studentLastNames =
            studentFirstNames.stream().map(name -> "Mac" + name).collect(Collectors.toList());
        Mockito.when(container.getStudentLastNames()).thenReturn(studentLastNames);

        Mockito.when(container.getCourseNames()).thenReturn(courseNames);
        Mockito.when(container.getStudentFirstNames()).thenReturn(studentFirstNames);
        Mockito.when(container.getStudentLastNames()).thenReturn(studentLastNames);

        expectedCourses.add(new Course.Builder().withName("Math").withDescription("Math is cool!!").build());
        expectedCourses.add(new Course.Builder().withName("Business").withDescription("Business is cool!!").build());

        IntStream.range(0, 200).forEach(
            i -> expectedStudents.add(new Student.Builder().withFirstName("Jen").withLastName("MacJen").build()));

        IntStream.range(0, 10).forEach(i -> studentsFromDB.add(new Student.Builder().withId(i).build()));

        groupsFromDB.add(new Group.Builder().withId(13).build());
        coursesFromDB.add(new Course.Builder().withId(13).build());

        Mockito.when(groupDao.findAll()).thenReturn(groupsFromDB);
        Mockito.when(studentDao.findAll()).thenReturn(studentsFromDB);
        Mockito.when(courseDao.findAll()).thenReturn(coursesFromDB);

        Mockito.when(rand.nextInt(studentFirstNames.size())).thenReturn(1);
        Mockito.when(rand.nextInt(studentLastNames.size())).thenReturn(1);
        Mockito.when(rand.nextInt(21)).thenReturn(0);
        Mockito.when(rand.nextInt(3)).thenReturn(0);
        Mockito.when(rand.nextInt(coursesFromDB.size())).thenReturn(0);
        dataGenerator.generateTestData();
    }

    @Test
    void generateTestData_shouldTakeCourseNamesFromDataContainer_whenRunGenerateCoursesMethod() throws SQLException {
        Mockito.verify(container, Mockito.times(1)).getCourseNames();
    }

    @Test
    void generateTestData_shouldFillCoursesList_whenRunGenerateCoursesMethod() throws SQLException {
        Assertions.assertEquals(expectedCourses, dataGenerator.getGeneratedCourses());
    }

    @Test
    void generateTestData_shouldCallCourseDaoAddMethod_whenRunGenerateCoursesMethod() throws SQLException {
        Mockito.verify(courseDao, Mockito.times(2)).addCourse(ArgumentMatchers.any(Course.class));
    }

    @Test
    void generateTestData_shouldGenerateTenGroups_whenRunGenerateGroupsMethod() throws SQLException {
        Assertions.assertEquals(10, dataGenerator.getGeneratedGroups().size());
    }

    @Test
    void generateTestData_shouldGenerateGroupsWithTwoLettersDashTwoNumbers_whenRunGenerateGroupsMethod() throws
        SQLException {
        List<Group> generatedGroups = dataGenerator.getGeneratedGroups();
        generatedGroups.stream().map(group -> group.getName().matches("[A-Z]{2}-[0-9]{2}"))
                       .forEach(Assertions::assertTrue);
    }

    @Test
    void generateTestData_shouldCallGroupDaoAddMethodForTenTimes_whenRunGenerateGroupsMethod() throws SQLException {
        Mockito.verify(groupDao, Mockito.times(10)).addGroup(ArgumentMatchers.any(Group.class));
    }

    @Test
    void generateTestData_shouldTakeFirstNamesFromDataContainer_whenRunGenerateStudentsMethod() throws SQLException {
        Mockito.verify(container, Mockito.times(1)).getStudentFirstNames();
    }

    @Test
    void generateTestData_shouldTakeLastNamesFromDataContainer_whenRunGenerateStudentsMethod() throws SQLException {
        Mockito.verify(container, Mockito.times(1)).getStudentLastNames();
    }

    @Test
    void generateTestData_shouldGenerateTwoHundredStudents_whenRunGenerateStudentsMethod() throws SQLException {
        Assertions.assertEquals(200, dataGenerator.getGeneratedStudents().size());
    }

    @Test
    void generateTestData_shouldGenerateTwoHundredStudentsWithTheSecondFirstNameAndTheSecondLastName_whenRandReturnsIndexOneInGenerateStudentsMethod() throws
        SQLException {
        Assertions.assertEquals(expectedStudents, dataGenerator.getGeneratedStudents());
    }

    @Test
    void generateTestData_shouldCallStudentDaoAddMethodForTwoHundredTimes_whenRunGenerateGroupsMethod() throws
        SQLException {
        Mockito.verify(studentDao, Mockito.times(200)).addStudent(ArgumentMatchers.any(Student.class));
    }

    @Test
    void generateTestData_shouldCallGroupDaoFindAllMethod_whenRunAssignStudentsToGroupsMethod() throws SQLException {
        Mockito.verify(groupDao, Mockito.times(1)).findAll();
    }

    @Test
    void generateTestData_shouldCallStudentDaoFindAllMethodTwoTime_whenRunAssignStudentsToGroupsAndAssignStudentsToCoursesMethods() throws
        SQLException {
        Mockito.verify(studentDao, Mockito.times(2)).findAll();
    }

    @Test
    void generateTestData_shouldCallStudentDaoUpdateGroupByStudentIdForEveryGroupFromDB_whenRunAssignStudentsToGroupsMethodAndRandomReturnZero() throws
        SQLException {
        Mockito.verify(studentDao, Mockito.times(10)).updateGroupByStudentId(ArgumentMatchers.anyInt(),
                                                                             ArgumentMatchers.anyInt());
    }

    @Test
    void generateTestData_shouldUpdateGroupByStudentIdFromOneToNineAndGroupIdThirteen_whenRunAssignStudentsToGroupsMethodAndRandomReturnZero() throws
        SQLException {
        for (int i = 1; i < 10; i++) {
            Mockito.verify(studentDao, Mockito.times(1)).updateGroupByStudentId(i, 13);
        }
    }

    @Test
    void generateTestData_shouldCallCourseDaoFindAllMethod_whenRunAssignStudentsToCoursesMethod() throws SQLException {
        Mockito.verify(courseDao, Mockito.times(1)).findAll();
    }

    @Test
    void generateTestData_shouldCallStudentDaoAddStudentToCourseMethodForEveryStudentFromDB_whenRunAssignStudentsToCoursesMethodAndRandomFromThreeReturnsZero() throws
        SQLException {
        Mockito.verify(studentDao, Mockito.times(studentsFromDB.size())).addStudentToCourse(ArgumentMatchers.anyInt(),
                                                                                            ArgumentMatchers.anyInt());
    }

    @Test
    void generateTestData_shouldAddToEachStudentFromDBCourseWithIdThirteen_whenRunAssignStudentsToCoursesMethodAndRandomFromThreeReturnsZeroAndFromCoursesFromDBSizeReturnZero() throws
        SQLException {
        for (int i = 0; i < studentsFromDB.size(); i++) {
            Mockito.verify(studentDao, Mockito.times(1)).addStudentToCourse(i, 13);
        }
    }


}
