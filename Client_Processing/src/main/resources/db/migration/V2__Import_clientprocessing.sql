INSERT INTO products (create_date, key, name, product_id)
VALUES ('2002-01-09' , 'PC','Soup', 'PC1'),
       ('2020-05-15', 'CC', 'Кредитная карта "Премиум"', 'CC2'),
       ('2021-03-20', 'AC', 'Автокредит "Стандарт"', 'AC3'),
       ('2022-11-10', 'IPO', 'Ипотечный кредит', 'IPO4'),
       ('2023-07-01', 'PC', 'Потребительский кредит', 'PC5');

INSERT INTO users (email, login, password)
VALUES ('Alex@mail.com' , 'Alex','Alex123'),
       ('anna.ivanova@mail.com', 'AnnaIvanova', 'Anna2024!'),
       ('petr.sidorov@bank.ru', 'PetrSidorov', 'Petr$Secure123'),
       ('olga.petrova@example.com', 'OlgaPetrova', 'Olga777Pass'),
       ('serg.vladimirov@finance.com', 'SergVlad', 'SergVVV888');

INSERT INTO clients (client_id, date_of_birth, document_id, document_prefix,
                     document_suffix, document_type, first_name, last_name, middle_name, user_id)
VALUES (770100000001,'2002-01-01','4511123456',
        '4511','123456','PASSPORT',
        'Alex', 'Jack','Misha','1'),
       (240100000001, '1985-08-20', '4602987654',
        '4602', '987654', 'PASSPORT',
        'Анна', 'Иванова', 'Петровна', 2),

       (550200000001, '1978-12-15', '651234567',
        '65', '1234567', 'INT_PASSPORT',
        'Петр', 'Сидоров', 'Владимирович', 3),

       (890100000001, '1995-03-10', 'III-AB789012',
        'III-AB', '789012', 'BIRTH_CERT',
        'Ольга', 'Петрова', 'Сергеевна', 4),

       (260300000001, '1980-07-25', '4805123456',
        '4805', '123456', 'PASSPORT',
        'Сергей', 'Владимиров', 'Игоревич', 5);

INSERT INTO client_products (close_date, open_date, status, client_id, product_id)
VALUES ('2015-02-02','2012-01-02','CLOSED','1','1'),
       (NULL, '2024-01-15', 'ACTIVE', 2, 2),
       ('2025-12-31', '2023-06-01', 'CLOSED', 3, 3),
       (NULL, '2024-03-20', 'BLOCKED', 4, 4),
       (NULL, '2024-02-10', 'ACTIVE', 5, 5);