insert into user(name)
values ('유저1');

insert into user(name)
values ('유저2');

insert into document(title, contents, drafter_id, category, approval_state, created_at)
values ('1번 문서', '내용1', 1, 'OPERATING_EXPENSES', 'DRAFTING', '2021-10-23 23:59:59');

insert into document(title, contents, drafter_id, category, approval_state, created_at)
values ('2번 문서', '내용2', 1, 'OPERATING_EXPENSES', 'APPROVED', '2021-10-24 23:59:59');