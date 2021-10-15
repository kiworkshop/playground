insert into user(id, name)
values (1, 'user1'),
       (2, 'user2'),
       (3, 'user3'),
       (4, 'user4'),
       (5, 'user5');


insert into document(id, title, category, contents, drafter_id, approval_state)
values (1, '문서1', '운영비', '제출합니다.', 1, '결재중'),
       (2, '문서2', '교육', '제출합니다.', 2, '결재중'),
       (3, '문서3', '교육', '제출합니다.', 2, '결재중'),
       (4, '문서4', '교육', '제출합니다.', 2, '결재중'),
       (5, '문서5', '물품구매', '제출합니다.', 3, '결재중');


insert into document_approval(id, approver_id, document_id, drafter_id, approval_state, approval_order,
                              approval_comment)
values (1, 2, 1, 1, '결재중', 1, ''),
       (2, 3, 1, 1, '결재중', 2, ''),
       (3, 4, 1, 1, '결재중', 3, ''),
       (4, 5, 1, 1, '결재중', 4, ''),
       (5, 1, 2, 2, '결재중', 1, ''),
       (6, 3, 2, 2, '결재중', 2, ''),
       (7, 1, 3, 3, '결재중', 1, ''),
       (8, 4, 3, 3, '결재중', 2, ''),
       (9, 5, 3, 3, '결재중', 3, ''),
       (10, 1, 4, 4, '결재중', 1, ''),
       (11, 3, 4, 4, '결재중', 2, ''),
       (12, 5, 4, 4, '결재중', 3, ''),
       (13, 2, 5, 5, '결재중', 1, '');