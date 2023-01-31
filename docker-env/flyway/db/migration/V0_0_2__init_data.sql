INSERT INTO subject (id, title)
VALUES ('ef97eb6f-3396-4b39-97f0-7eecf7510d56', 'ENGLISH'),
       ('119f32c5-fc48-4f12-a04c-7fb0a47c617f', 'GERMAN'),
       ('dd1783ea-4d14-4a56-b279-5abee842ae19', 'FRENCH'),
       ('234bcf9a-e814-4579-8c91-fd2fe6932c34', 'RUSSIAN'),
       ('1f8867e6-7555-4456-8c26-b7fb1fca0a34', 'MALIAN'),
       ('1358a9a8-8459-4a28-8b18-e63c6697e233', 'JAPANESE'),
       ('cdca2395-13ba-47f3-b312-0bfbd79bfe52', 'PORTUGUESE'),
       ('09dffbf5-7639-49d9-a142-34bbae4f2304', 'ARABIC'),
       ('982f6415-b05c-460a-9fc3-45b6c5005c34', 'SPANISH'),
       ('9ff47c27-5192-47ed-88b8-3f80c84efe02', 'CHINESE');

INSERT INTO email_template (id, subject, template_server_file_name, email_type)
VALUES ('ef97eb6f-3396-4b39-97f0-7eecf7510d56'
       , 'Please verify your registration'
       , '131bba98-1002-4b51-977e-c91e05b45765.html'
       , 'REGISTRATION');