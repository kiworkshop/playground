insert into user(id, name, password, email, insert_date)
values (1, 'user1', 'pa@sw**d', 'user1@gmail.com', '2021-11-17 15:50:57'),
       (2, 'user2', 'pa@sw**d', 'user2@gmail.com', '2021-11-17 15:50:57'),
       (3, 'user3', 'pa@sw**d', 'user3@gmail.com', '2021-11-17 15:50:57');


insert into document(id, title, category, contents, drafter_id, approval_state, insert_date)
values (1, '문서1', 'OPERATING_EXPENSES', '운영비 결재 요청드립니다.', 1, 'APPROVED', '2021-11-17 16:50:57'),
       (2, '문서2', 'EDUCATION', '사외교육비 결재 요청드립니다.', 2, 'DRAFTING', '2021-11-17 16:50:57'),
       (3, '문서3', 'EDUCATION', '사외교육비 결재 요청드립니다.', 3, 'CANCELED', '2021-11-17 16:50:57');


insert into document_approval(id, approver_id, document_id, approval_state, approval_order, approval_comment, insert_date)
values (1, 1, 1, 'APPROVED', 1, '', '2021-11-17 17:50:57'),
       (2, 2, 1, 'APPROVED', 2, '', '2021-11-17 18:50:57'),
       (3, 3, 1, 'APPROVED', 3, '', '2021-11-17 19:50:57'),

       (4, 2, 2, 'APPROVED', 1, '', '2021-11-17 17:50:57'),
       (5, 1, 2, 'DRAFTING', 2, '', '2021-11-17 18:50:57'),
       (6, 3, 2, 'DRAFTING', 3, '', '2021-11-17 19:50:57'),

       (7, 3, 3, 'APPROVED', 1, '', '2021-11-17 17:50:57'),
       (8, 1, 3, 'CANCELED', 2, '', '2021-11-17 19:50:57');
