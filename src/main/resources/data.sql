drop table if exists user;
drop table if exists document;

create table user
(
    id   bigint       not null auto_increment primary key,
    name varchar(255) not null
);


create table document
(
    id             bigint       not null auto_increment primary key,
    title          varchar(255) not null,
    contents       varchar(255),
    drafter_id     bigint       not null,
    category       varchar(255) not null,
    approval_state varchar(255) not null,
    foreign key (drafter_id) references user (id)

);

insert into user(name)
values ('유저1');

insert into user(name)
values ('유저2');

insert into document(title, contents, drafter_id, category, approval_state)
values ('1번 문서', '내용1', 1, 'OPERATING_EXPENSES', 'DRAFTING');

insert into document(title, contents, drafter_id, category, approval_state)
values ('2번 문서', '내용2', 1, 'OPERATING_EXPENSES', 'APPROVED');