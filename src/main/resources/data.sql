drop table if exists user;

create table user
(
    id       bigint not null auto_increment primary key,
    email    varchar(255),
    password varchar(255),
    name     varchar(255)
);

insert into user(id, email, password, name)
values (1, 'wbluke@gmail.com', '1234', '박우빈');

insert into user(id, email, password, name)
values (2, 'wbluke2@gmail.com', '1234', '닉우빈');