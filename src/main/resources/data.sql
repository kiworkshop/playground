drop table if exists user;
create table user
(
    id       bigint not null auto_increment primary key,
    email    varchar(255),
    password varchar(255),
    name     varchar(255),
    constraint user_email_constraint unique (email)
);

drop table if exists document;
create table document
(
    id             bigint       not null auto_increment primary key,
    title          varchar(255) not null,
    category       varchar(255) not null,
    contents       varchar(255) not null,
    drafter_id     bigint       not null,
    approval_state varchar(255) not null,
    foreign key (drafter_id) references user (id)
);

drop table if exists documentApproval;
create table document_approval
(
    approver_id      bigint       not null,
    document_id      bigint       not null,
    approval_state   varchar(255) not null,
    approval_order   tinyint      not null,
    approval_comment varchar(255),
    foreign key (approver_id) references user (id),
    foreign key (document_id) references document (id)
);

insert into user(id, email, password, name)
values (1, 'wbluke@gmail.com', '1234', '박우빈');

insert into user(id, email, password, name)
values (2, 'wbluke2@gmail.com', '1234', '닉우빈');
