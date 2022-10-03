package ua.foxminded.herasimov.task7.entity;

import java.util.Objects;
import java.util.Set;

public class Student implements Entity {

    private Integer id;
    private String firstName;
    private String lastName;
    private Integer groupId;
    private Set<Course> courses;

    public static class Builder {
        private Student newStudent = new Student();

        public Builder withId(int id) {
            newStudent.id = id;
            return this;
        }

        public Builder withFirstName(String firstName) {
            newStudent.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            newStudent.lastName = lastName;
            return this;
        }

        public Builder withGroupId(int groupId) {
            newStudent.groupId = groupId;
            return this;
        }

        public Builder withCourses(Set<Course> courses) {
            newStudent.courses = courses;
            return this;
        }

        public Student build() {
            return newStudent;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Student{" +
               "id=" + id +
               ", firstName='" + firstName + '\'' +
               ", lastName='" + lastName + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(groupId, student.groupId) && Objects.equals(firstName, student.firstName) &&
               Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, firstName, lastName);
    }
}
