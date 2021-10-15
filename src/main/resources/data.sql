drop table if exists document;

create table document
(
    id bigint not null auto_increment primary key,
    title varchar(255),
    category varchar(255),
    contents longvarchar,
    userid int,
    approvalState varchar(255),
    userName varchar(255),
    categoryText varchar(255),
    approvalStateText varchar(255)
);

insert into document(id, title, category, contents, userid, approvalstate, username, categorytext, approvalstatetext)
values(default, '팀 운영비 사용 정산의 건', 'OPERATING_EXPENSES', '운영비 사용 내역입니다.', 1, 'DRAFTING', '박우빈', '운영비', '기안');

insert into document
values(default, '문건2 제목', 'OPERATING_EXPENSES', '문건2 내용', 1, 'DRAFTING', '박우빈', '운영비', '기안');

insert into document
values(default, '문건3 제목', 'OPERATING_EXPENSES', '문건3 내용', 1, 'DRAFTING', '박우빈', '운영비', '기안');