insert into team(id, name) values (1, '정산시스템팀');
insert into team(id, name) values (2, '서비스개발팀');

insert into user(id, name, rank, team_id, email, password) values (1, '박우빈', 'TEAM_LEADER', 1, 'aa@naver.com', '1212');
insert into user(id, name, rank, team_id, email, password) values (2, '이다은', 'PART_MANAGER', 1, 'aa@naver.com', '1212');
insert into user(id, name, rank, team_id, email, password) values (3, '박우빈2', 'TEAM_LEADER', 2, 'aa@naver.com', '1212');
insert into user(id, name, rank, team_id, email, password) values (4, '이다은2', 'PART_MANAGER', 2, 'aa@naver.com', '1212');

--insert into document(id, title, category, contents, drafter_id, approval_state)
--    values (1, '첫번째 문서제목', 'EDUCATION', '문서내용', 1, 'DRAFTING');

