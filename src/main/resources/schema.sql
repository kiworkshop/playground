-- drop table if exists user CASCADE;
create table user
(
    id          bigint generated by default as identity,
    name        varchar(100),
    insert_date timestamp default now(),
    update_date timestamp,
    primary key (id)
);

-- drop table if exists document CASCADE;
create table document
(
    id             bigint generated by default as identity,
    title          varchar(255),
    category       varchar(100),
    contents       clob,
    drafter_id     bigint,
    drafter_name   varchar(100),
    approval_state varchar(100),
    insert_date    timestamp default now(),
    update_date    timestamp,
    primary key (id)
);

-- drop table if exists document_approval CASCADE;
create table document_approval
(
    id               bigint generated by default as identity,
    approver_id      bigint,
    document_id      bigint,
    drafter_id       bigint,
    approval_state   varchar(100),
    approval_order   bigint,
    approval_comment clob,
    primary key (id)
);
