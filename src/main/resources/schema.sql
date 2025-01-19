drop table if exists products;
create table products
(
    id          int auto_increment primary key,
    title       varchar(255) not null,
    description varchar(255) not null,
    image       varchar(255) not null,
    price       float          not null
);