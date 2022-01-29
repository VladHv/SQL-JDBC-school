DROP TABLE IF EXISTS groups, students, courses;
CREATE TABLE groups
(
    group_id SERIAL PRIMARY KEY,
    name     VARCHAR(255)
);
CREATE TABLE students
(
    student_id SERIAL PRIMARY KEY,
    group_id   INTEGER REFERENCES groups (group_id),
    first_name VARCHAR(255),
    last_name  VARCHAR(255)
);
CREATE TABLE courses
(
    course_id   SERIAL PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255)
);
