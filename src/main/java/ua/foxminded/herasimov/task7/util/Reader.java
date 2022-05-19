package ua.foxminded.herasimov.task7.util;

import java.util.Scanner;

public class Reader {

    private final Scanner scanner = new Scanner(System.in);

    public int readInt() {
        return scanner.nextInt();
    }

    public String readString() {
        return  scanner.next();
    }
}
