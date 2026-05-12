CREATE TABLE IF NOT EXISTS transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(255) NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_date DATE NOT NULL
);

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('John', 120, '2025-01-15');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('John', 75, '2025-01-20');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('John', 110, '2025-02-10');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('John', 45, '2025-02-18');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('John', 80, '2025-03-05');



INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('Alice', 90, '2025-01-20');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('Alice', 40, '2025-02-18');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('Alice', 140, '2025-03-22');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('Alice', 220, '2025-03-25');



INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('Bob', 200, '2025-01-12');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('Bob', 75, '2025-02-28');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('Bob', 30, '2025-03-02');



INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('David', 51, '2025-01-05');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('David', 101, '2025-02-08');

INSERT INTO transactions (customer_name, amount, transaction_date)
VALUES ('David', 99, '2025-03-10');