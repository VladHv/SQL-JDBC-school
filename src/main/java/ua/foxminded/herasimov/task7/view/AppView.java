package ua.foxminded.herasimov.task7.view;

public class AppView {

    public static final String MAIN_MENU = "1. Find all groups with less or equals student count\n" +
                                    "2. Find all students related to course with given name\n" +
                                    "3. Add new student\n" +
                                    "4. Delete student by STUDENT_ID\n" +
                                    "5. Add a student to the course (from a list)\n" +
                                    "6. Remove the student from one of his or her courses\n" +
                                    "other - EXIT.";
    public static final String CHOOSE = "Choose function: ";
    public static final String FIRST_NAME = "First name: ";
    public static final String LAST_NAME = "Last name: ";
    public static final String COURSE_ID = "Input course ID: ";
    public static final String STUDENT_ID = "Input student ID: ";
    public static final String STUDENT_COUNT = "Input student count: ";
    public static final String EMPTY = "Empty!";


    public void showMessage(String message) {
        System.out.println(message);
    }

}
