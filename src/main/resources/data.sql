INSERT INTO task(id, description, status, time_limit, title)
VALUES (1, 'Descryption test', 'Pending', CURRENT_TIMESTAMP, 'Rest test'),
       (2, 'Descryption test', 'During', CURRENT_TIMESTAMP, 'Debugging');

INSERT INTO users(id, email, name, surname, task_id)
VALUES (1, 'tomek@mail.com', 'Tomek', 'Kowalski', 1),
        (2, 'adam@mail.com', 'Adam', 'Nowak', null),
        (3, 'adam2@mail.com', 'Adam', 'Kowal', null),
        (4, 'adam3@mail.com', 'Adam', 'Kamyszek', null),
        (5, 'marcin@mail.com','Marcin', 'Kowal', null),
        (6, 'karolina@mail.com','Karolina', 'Larton', 1);