package ua.foxminded.herasimov.task7.util;

import java.util.Scanner;

public class Reader {

    private final Scanner scanner = new Scanner(System.in);

    public int askInt() {
        return scanner.nextInt();
    }

    public String askString() {
        return  scanner.next();
    }
}
