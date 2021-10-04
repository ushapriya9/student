create table student(
    roll_number NUMBER,
    name VARCHAR,
    student_class VARCHAR,
    Primary key ( roll_number )
);

create table student_marks(
    roll_number NUMBER,
    subject VARCHAR,
    obtained_marks NUMBER,
    out_of_marks NUMBER default 100,
    Primary key ( roll_number, subject ),
    Foreign Key (roll_number) references Student(roll_number)
);