package ua.foxminded.herasimov.task7.entity;

import java.util.Objects;

public class Group {

    private Integer id;
    private String name;

    public static class Builder {
        private Group newGroup;

        public Builder() {
            newGroup = new Group();
        }

        public Builder withId(int id) {
            newGroup.id = id;
            return this;
        }

        public Builder withName(String name) {
            newGroup.name = name;
            return this;
        }

        public Group build() {
            return newGroup;
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

    @Override
    public String toString() {
        return "Group{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
