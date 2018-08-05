/* Populate tables */
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Andres', 'Guzman', 'profesor@bolsadeideas.com', '2017-08-01', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('John', 'Doe', 'john.doe@gmail.com', '2017-08-02', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Linus', 'Torvalds', 'linus.torvalds@gmail.com', '2017-08-03', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Jane', 'Doe', 'jane.doe@gmail.com', '2017-08-04', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Rasmus', 'Lerdorf', 'rasmus.lerdorf@gmail.com', '2017-08-05', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Erich', 'Gamma', 'erich.gamma@gmail.com', '2017-08-06', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Richard', 'Helm', 'richard.helm@gmail.com', '2017-08-07', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Ralph', 'Johnson', 'ralph.johnson@gmail.com', '2017-08-08', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('John', 'Vlissides', 'john.vlissides@gmail.com', '2017-08-09', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('James', 'Gosling', 'james.gosling@gmail.com', '2017-08-010', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Bruce', 'Lee', 'bruce.lee@gmail.com', '2017-08-11', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Johnny', 'Doe', 'johnny.doe@gmail.com', '2017-08-12', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('John', 'Roe', 'john.roe@gmail.com', '2017-08-13', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Jane', 'Roe', 'jane.roe@gmail.com', '2017-08-14', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Richard', 'Doe', 'richard.doe@gmail.com', '2017-08-15', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Janie', 'Doe', 'janie.doe@gmail.com', '2017-08-16', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Phillip', 'Webb', 'phillip.webb@gmail.com', '2017-08-17', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Stephane', 'Nicoll', 'stephane.nicoll@gmail.com', '2017-08-18', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Sam', 'Brannen', 'sam.brannen@gmail.com', '2017-08-19', '');  
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Juergen', 'Hoeller', 'juergen.Hoeller@gmail.com', '2017-08-20', ''); 
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Janie', 'Roe', 'janie.roe@gmail.com', '2017-08-21', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('John', 'Smith', 'john.smith@gmail.com', '2017-08-22', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Joe', 'Bloggs', 'joe.bloggs@gmail.com', '2017-08-23', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('John', 'Stiles', 'john.stiles@gmail.com', '2017-08-24', '');
INSERT INTO Clients (name, surname, email, created_at, picture) VALUES('Richard', 'Roe', 'stiles.roe@gmail.com', '2017-08-25', '');

/* Populate tabla Products */
INSERT INTO Products (name, price, created_at) VALUES('Panasonic Pantalla LCD', 1299, NOW());
INSERT INTO Products (name, price, created_at) VALUES('Sony Camara digital DSC-W320B', 469, NOW());
INSERT INTO Products (name, price, created_at) VALUES('Apple iPhone X', 1159, NOW());
INSERT INTO Products (name, price, created_at) VALUES('Sony Notebook Z110', 399, NOW());
INSERT INTO Products (name, price, created_at) VALUES('Hewlett Packard Multifuncional F2280', 116, NOW());
INSERT INTO Products (name, price, created_at) VALUES('Bianchi Bicicleta Aro 26', 300, NOW());
INSERT INTO Products (name, price, created_at) VALUES('Mica Comoda 5 Cajones', 100, NOW());


INSERT INTO Invoices (date, description, observation, client_id) VALUES (NOW(), 'Inoice #1 DEMO', 'This is an observation', 1);
INSERT INTO Invoice_detail (amount, product_id, invoice_id) VALUES (1, 1, 1);
INSERT INTO Invoice_detail (amount, product_id, invoice_id) VALUES (3, 4, 1);
INSERT INTO Invoice_detail (amount, product_id, invoice_id) VALUES (2, 2, 1);

INSERT INTO Invoices (date, description, observation, client_id) VALUES (NOW(), 'Inoice #2 DEMO 2', 'This is an observation 2', 1);
INSERT INTO Invoice_detail (amount, product_id, invoice_id) VALUES (1, 2, 2);

--users and roles

INSERT INTO users (username, password, enabled) VALUES ('carlos', '$2a$10$PYeso//N1xRnxpkJxWybeeb8kIAFCZfV32X1V7ODOtfvR7UPFTBqe', 1);
INSERT INTO users (username, password, enabled) VALUES ('admin', '$2a$10$KICcfggp0bEPcJXp1vDYUerxvbLwhYkug8q5JdV9lzT6mvyjBUaUO', 1);

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_ADMIN');