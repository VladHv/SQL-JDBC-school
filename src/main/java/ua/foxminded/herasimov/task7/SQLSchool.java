package ua.foxminded.herasimov.task7;

public class SQLSchool {
    public static void main(String[] args) {

        String sqlScriptFileName = "create_tables.sql";

        TableCreator creator = new TableCreator();
        creator.createEmptyTables(sqlScriptFileName);

    }
}
