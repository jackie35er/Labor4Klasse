create table students (
    last_name varchar(255),
    first_name varchar(255),
    gender varchar(30),
    student_number int,
    class varchar(5),
    constraint students_pk primary key (class, student_number)
);
