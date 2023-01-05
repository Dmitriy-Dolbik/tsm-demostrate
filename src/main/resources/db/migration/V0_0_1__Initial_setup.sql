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

INSERT INTO subject (id, title) VALUES
                                    (ef97eb6f-3396-4b39-97f0-7eecf7510d56,ENGLISH),
                                    (119f32c5-fc48-4f12-a04c-7fb0a47c617f,GERMAN),
                                    (dd1783ea-4d14-4a56-b279-5abee842ae19,FRENCH),
                                    (234bcf9a-e814-4579-8c91-fd2fe6932c34,RUSSIAN),
                                    (1f8867e6-7555-4456-8c26-b7fb1fca0a34,MALIAN),
                                    (1358a9a8-8459-4a28-8b18-e63c6697e233,JAPANESE),
                                    (cdca2395-13ba-47f3-b312-0bfbd79bfe52,PORTUGUESE),
                                    (09dffbf5-7639-49d9-a142-34bbae4f2304,ARABIC),
                                    (982f6415-b05c-460a-9fc3-45b6c5005c34,SPANISH),
                                    (9ff47c27-5192-47ed-88b8-3f80c84efe02,CHINESE);