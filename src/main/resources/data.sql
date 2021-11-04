drop table if exists user;
drop table if exists document;
drop table if exists document_approval;
create table user
(
    id       bigint not null auto_increment primary key,
    name     varchar(255),
);
create table document
(
    id bigint not null auto_increment primary key,
    title           varchar(255),
    category        varchar(255),
    content         text,
    drafter         varchar(255),
    approval_state  varchar(255)
);
create table document_approval
(
    id                  bigint not null auto_increment primary key,
    document_id         bigint,
    approver            varchar(255),
    approval_order      bigint,
    approval_comment    varchar(255)
);


--insert into user(id, email, password, name)
--values (1, 'wbluke@gmail.com', '1234', '박우빈');
--
--insert into user(id, email, password, name)
--values (2, 'wbluke2@gmail.com', '1234', '닉우빈');