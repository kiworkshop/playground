drop table if exists user;
drop table if exists document;
drop table if exists document_approval;

create table user
(
    id       bigint not null auto_increment primary key,
    name     varchar(255)
);
create table document
(
    id              bigint not null auto_increment primary key,
    title           varchar(255),
    category        varchar(255),
    contents        text,
    drafter_id      varchar(255),
    approval_state  varchar(255),
    created_at         DATETIME DEFAULT NOW()
);
create table document_approval
(
    id                  bigint not null auto_increment primary key,
    document_id         bigint,
    approver_id            varchar(255),
    approval_order      bigint,
    approval_comment    varchar(255)
);


insert into user(id, name)
values (1, '박우빈');
insert into user(id, name)
values (2, '이다은');

insert into document(id, title, category, contents, drafter_id, approval_state)
    values (1, '첫번째 문서제목', 'EDUCATION', '문서내용', 1, 'DRAFTING');
--insert into user(id, email, password, name)
--values (2, 'wbluke2@gmail.com', '1234', '닉우빈');