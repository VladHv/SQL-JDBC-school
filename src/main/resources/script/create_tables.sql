DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS students CASCADE;
DROP TABLE IF EXISTS courses CASCADE;
DROP TABLE IF EXISTS students_courses CASCADE;
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

CREATE TABLE students_courses
(
    student_id INTEGER REFERENCES students (student_id) ON UPDATE CASCADE ON DELETE CASCADE,
    course_id INTEGER REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE
);
