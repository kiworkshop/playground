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
    created_at     timestamp    not null,
    foreign key (drafter_id) references user (id)
);

create table document_approval
(
    id               bigint       not null auto_increment primary key,
    approver_id      bigint       not null,
    approval_state   varchar(255) not null,
    approval_comment varchar(255),
    foreign key (approver_id) references user (id)
);