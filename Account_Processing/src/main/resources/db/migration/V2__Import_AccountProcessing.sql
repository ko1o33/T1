INSERT INTO accounts (balance, card_exist, client_id, interest_rate, is_recalc, product_id, status)
VALUES (7000, true, 1 ,22,true,1,'CLOSED'),
       (25000, true, 2, 3.5, true, 2, 'ACTIVE'),
       (-15000, true, 3, 18.9, true, 3, 'ACTIVE'),
       (100000, false, 4, 7.2, true, 4, 'ACTIVE'),
       (5000, true, 5, 2.1, false, 5, 'BLOCKED');

INSERT INTO cards(card_id, payment_system, status, account_id)
VALUES ('1232567812345678','VISA','ACTIVE',1),
       ('1234567842349867', 'MASTERCARD', 'ACTIVE', 2),
       ('8765432187654321', 'VISA', 'BLOCKED', 3),
       ('5555666677778888', 'MIR', 'ARRESTED', 4),
       ('9999888877776666', 'VISA', 'ACTIVE', 2);

INSERT INTO payments(account_id, amount, is_credit, payment_date, type)
VALUES (1,5000, false, '2017-01-05','WITHDRAWAL'),
       (2, 15000, true, '2024-01-15', 'DEPOSIT'),
       (3, 5000, false, '2024-02-10', 'LOAN_PAYMENT'),
       (4, 50000, true, '2024-03-01', 'DEPOSIT'),
       (4, 2500, true, '2024-03-05', 'INTEREST');

insert into transactions(account_id, amount, status, timestamp, type, card_id)
VALUES (1,5000,'COMPLETE','2017-01-05','WITHDRAWAL',1),
       (2, 5000, 'COMPLETE', '2024-01-20', 'DEPOSIT', 2),
       (3, 10000, 'BLOCKED', '2024-02-15', 'INTEREST', 3),
       (4, 20000, 'COMPLETE', '2024-03-10', 'LOAN_PAYMENT', 2);