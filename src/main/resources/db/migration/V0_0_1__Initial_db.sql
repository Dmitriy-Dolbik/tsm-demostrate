CREATE TABLE Users
(
    id                varchar PRIMARY KEY,
    first_name        varchar(255),
    last_name         varchar(255),
    email             varchar(255) NOT NULL UNIQUE,
    password          varchar(255) NOT NULL,
    age               smallint,
    phone_number      varchar(255),
    role              varchar(255) NOT NULL,
    status            varchar(255),
    created_date      timestamp,
    verified          boolean,
    verification_code varchar(255)
);
CREATE TABLE student_created_by_teacher
(
    id           varchar PRIMARY KEY,
    first_name   varchar(255),
    last_name    varchar(255),
    email        varchar(255) NOT NULL UNIQUE,
    age          smallint,
    phone_number varchar(255) UNIQUE,
    status       varchar(255),
    balance      decimal,
    user_id      varchar REFERENCES Users (id) ON DELETE CASCADE,
    created_date timestamp with time zone
);
CREATE TABLE Lesson
(
    id           varchar PRIMARY KEY,
    status       varchar(255),
    comment      varchar(255),
    price        smallint,
    start_time   timestamp with time zone,
    stop_time    timestamp with time zone,
    user_id      varchar REFERENCES Users (id) ON DELETE CASCADE,
    created_date timestamp with time zone
);
CREATE TABLE Social_media
(
    id           varchar PRIMARY KEY,
    title        varchar(255),
    link         varchar(255),
    user_id      varchar REFERENCES Users (id) ON DELETE CASCADE,
    created_date timestamp with time zone
);
CREATE TABLE Photo
(
    id             varchar PRIMARY KEY,
    title          varchar(255),
    serverFileName varchar(255),
    user_id        varchar REFERENCES Users (id) ON DELETE CASCADE
);
CREATE TABLE Subject
(
    id    varchar PRIMARY KEY,
    title varchar(255)
);
CREATE TABLE Knowledge_level
(
    id                            varchar PRIMARY KEY,
    student_level                 varchar(255),
    student_created_by_teacher_id varchar REFERENCES student_created_by_teacher (id) ON DELETE CASCADE,
    subject_id                    varchar REFERENCES Subject (id) ON DELETE CASCADE
);
CREATE TABLE teacher_subject
(
    teacher_id varchar REFERENCES Users (id) ON DELETE CASCADE,
    subject_id varchar REFERENCES Subject (id) ON DELETE CASCADE,
    PRIMARY KEY (teacher_id, subject_id)
);
CREATE TABLE teacher_student_created_by_teacher
(
    teacher_id                    varchar REFERENCES Users (id) ON DELETE CASCADE,
    student_created_by_teacher_id varchar REFERENCES student_created_by_teacher (id) ON DELETE CASCADE,
    PRIMARY KEY (teacher_id, student_created_by_teacher_id)
);
CREATE TABLE lesson_student_created_by_teacher
(
    lesson_id                     varchar REFERENCES Lesson (id) ON DELETE CASCADE,
    student_created_by_teacher_id varchar REFERENCES student_created_by_teacher (id) ON DELETE CASCADE,
    PRIMARY KEY (lesson_id, student_created_by_teacher_id)
);
CREATE TABLE email_template
(
    id                        varchar PRIMARY KEY,
    subject                   varchar(255),
    template_server_file_name varchar,
    email_type                varchar(255)
);

