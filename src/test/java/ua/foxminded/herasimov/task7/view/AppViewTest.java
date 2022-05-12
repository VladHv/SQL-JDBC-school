package ua.foxminded.herasimov.task7.view;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppViewTest {

    private final AppView view = new AppView();
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @Test
    void showActionResult_shouldPrintSuccess_whenInputValueMoreThanZero() {
        System.setOut(new PrintStream(output));
        view.showActionResult(1);
        assertEquals("Success!\r\n", output.toString());
    }

    @Test
    void showActionResult_shouldPrintCautionText_whenInputValueLessOrEqualZero() {
        System.setOut(new PrintStream(output));
        view.showActionResult(0);
        assertEquals("Something went wrong!\r\n", output.toString());
    }
}
