create table Category
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(40)
);

create table Product
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(40),
    price       DOUBLE,
    currency    VARCHAR(10),
    category_id bigint,
    foreign key (category_id) references Category (id)
);