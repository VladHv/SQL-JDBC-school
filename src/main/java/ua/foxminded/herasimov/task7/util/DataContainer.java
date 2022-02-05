package ua.foxminded.herasimov.task7.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataContainer {

    private final List<String> courseNames;
    private final List<String> studentFirstNames;
    private final List<String> studentLastNames;


    {
        courseNames = new ArrayList<>();
        courseNames.add("Math");
        courseNames.add("Biology");
        courseNames.add("Business");
        courseNames.add("Economics");
        courseNames.add("Psychology");
        courseNames.add("Computer Science");
        courseNames.add("Communications");
        courseNames.add("Nursing");
        courseNames.add("Philosophy");
        courseNames.add("Science");

        studentFirstNames = new ArrayList<>();
        studentFirstNames.add("Bunny");
        studentFirstNames.add("Norton");
        studentFirstNames.add("Cammie");
        studentFirstNames.add("Purdie");
        studentFirstNames.add("Cale");
        studentFirstNames.add("Kae");
        studentFirstNames.add("Alec");
        studentFirstNames.add("Aubree");
        studentFirstNames.add("Earnest");
        studentFirstNames.add("Frieda");
        studentFirstNames.add("Jacob");
        studentFirstNames.add("Pleasance");
        studentFirstNames.add("Berny");
        studentFirstNames.add("Marshal");
        studentFirstNames.add("Nelle");
        studentFirstNames.add("Laurelle");
        studentFirstNames.add("Lissa");
        studentFirstNames.add("Casimir");
        studentFirstNames.add("Hyacinth");
        studentFirstNames.add("Jannette");

        studentLastNames =
            studentFirstNames.stream().map(name -> "Mac" + name).collect(Collectors.toList());

    }

    public List<String> getCourseNames() {
        return courseNames;
    }

    public List<String> getStudentFirstNames() {
        return studentFirstNames;
    }

    public List<String> getStudentLastNames() {
        return studentLastNames;
    }
}
