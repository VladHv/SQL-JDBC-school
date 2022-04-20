package ua.foxminded.herasimov.task7.entity;

import java.util.Objects;
import java.util.Set;

public class Course {

    private Integer id;
    private String name;
    private String description;
    private Set<Student> students;

    public static class Builder {
        private Course newCourse;

        public Builder() {
            newCourse = new Course();
        }

        public Builder withId(Integer id) {
            newCourse.id = id;
            return this;
        }

        public Builder withName(String name) {
            newCourse.name = name;
            return this;
        }

        public Builder withDescription(String description) {
            newCourse.description = description;
            return this;
        }

        public Builder withStudents(Set<Student> students) {
            newCourse.students = students;
            return this;
        }

        public Course build() {
            return newCourse;
        }


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", students=" + students +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return name.equals(course.name) && description.equals(course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
