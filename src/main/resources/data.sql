insert into user(id, email, password, name)
values (1, 'wbluke@gmail.com', '1234', '박우빈');

insert into user(id, email, password, name)
values (2, 'wbluke2@gmail.com', '1234', '닉우빈');

insert into document(id, title, category, contents, approval_state, drafter_id)
values (1, '팀 운영비 사용 정산의 건', 'OPERATING_EXPENSES', '운영비 사용 내역입니다.', 'DRAFTING', 1);

insert into document(id, title, category, contents, approval_state, drafter_id)
values (2, '도서지원비 정산의 건', 'EDUCATION', '도서지원비 사용 내역입니다.', 'DRAFTING', 1);

insert into document_approval(id, approval_order, approval_state, approver_id, document_id, approval_comment)
values (1, 1, 'APPROVED', 1, 1, null);

insert into document_approval(id, approval_order, approval_state, approver_id, document_id, approval_comment)
values (2, 2, 'DRAFTING', 2, 1, null);

insert into document_approval(id, approval_order, approval_state, approver_id, document_id, approval_comment)
values (3, 3, 'DRAFTING', 3, 1, null);

insert into document_approval(id, approval_order, approval_state, approver_id, document_id, approval_comment)
values (4, 1, 'APPROVED', 1, 2, '감사합니다.');
