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
    course_id  INTEGER REFERENCES courses (course_id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO groups (name) VALUES ('AA-11');
INSERT INTO groups (name) VALUES ('BB-22');
INSERT INTO groups (name) VALUES ('CC-33');

INSERT INTO students (group_id, first_name, last_name) VALUES (1, 'John', 'Smith');
INSERT INTO students (group_id, first_name, last_name) VALUES (2, 'Anna', 'Black');
INSERT INTO students (group_id, first_name, last_name) VALUES (1, 'Bob', 'Dawn');

INSERT INTO courses (name, description) VALUES ('Math', 'Math is bad!');
INSERT INTO courses (name, description) VALUES ('Biology', 'Biology is bad!');
INSERT INTO courses (name, description) VALUES ('Science', 'Science is bad!');

INSERT INTO students_courses (student_id, course_id) VALUES (1, 3);
INSERT INTO students_courses (student_id, course_id) VALUES (2, 2);
INSERT INTO students_courses (student_id, course_id) VALUES (3, 1);

