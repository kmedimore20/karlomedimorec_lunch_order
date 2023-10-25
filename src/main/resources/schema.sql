CREATE TABLE MENU(
    meal_id int auto_increment,
    meal_name varchar (255) NOT NULL,
    price double NOT NULL
);

INSERT INTO MENU (meal_name, price) VALUES
    ('Varivo od mix mahunarki', 3.6),
    ('Pohani file oslića – krumpir salata s rikulom', 6.30),
    ('Pohani file oslića, umak od vlasca i krastavca - slani krumpir', 6.30),
    ('Steak tune sa žara, tršćanski umak – blitva s krumpirom', 10.00),
    ('Orada sa žara, tršćanski umak – blitva s krumpirom', 7.10),
    ('Crni rižoto od liganja s parmezanom', 6.50),
    ('Pureći medaljoni u umaku od pesta s tjesteninom', 6),
    ('Juha od rajčice', 1.20),
    ('Salata miješana', 1.10);

CREATE TABLE ORDERS(
    order_id int auto_increment,
    menu_items varchar (255) NOT NULL
);

CREATE SEQUENCE ORDERS_SEQUENCE_ID START WITH (select max(order_id) + 1 from ORDERS);

