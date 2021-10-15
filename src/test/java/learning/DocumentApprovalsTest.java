package learning;

import fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentApprovalsTest {

    @Test
    @DisplayName("결재자를 추가한다.")
    void add() {
        // given
        User approver1 = UserFixture.create(1L, "결재자1");
        DocumentApproval documentApproval = DocumentApproval.of(approver1, 1);
        DocumentApprovals documentApprovals = new DocumentApprovals();

        // when
        documentApprovals.add(documentApproval);

        // then
        assertThat(documentApprovals.list())
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(
                        tuple(approver1.getId(), ApprovalState.DRAFTING, 1, null)
                );
    }

    @Test
    @DisplayName("결재자가 추가되지 않는다.")
    void add_fail() {
        // given
        DocumentApprovals documentApprovals = new DocumentApprovals();

        // when
        documentApprovals.add(null);

        // then
        assertThat(documentApprovals.list()).isEmpty();
    }

    @Test
    @DisplayName("문서를 결재하는 경우 내가 결재할 차례면 결재가 성공한다.")
    void approveSuccess() {
        // given
        User approver1 = UserFixture.create(1L, "결재자1");
        User approver2 = UserFixture.create(2L, "결재자2");

        DocumentApproval documentApproval1 = DocumentApproval.of(approver1, 1);
        DocumentApproval documentApproval2 = DocumentApproval.of(approver2, 2);

        DocumentApprovals documentApprovals = new DocumentApprovals();
        documentApprovals.add(documentApproval1);
        documentApprovals.add(documentApproval2);
        String approvalComment = "결재 승인합니다.";

        // when
        documentApprovals.approveBy(approver1, approvalComment);

        // then
        assertThat(documentApprovals.list())
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(
                        tuple(approver1.getId(), ApprovalState.APPROVED, 1, approvalComment),
                        tuple(approver2.getId(), ApprovalState.DRAFTING, 2, null)
                );
    }

    @Test
    @DisplayName("모든 결재자가 승인할 경우, 참을 반환한다.")
    void isAllApproved_true() {
        // given
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        DocumentApproval documentApproval1 = DocumentApproval.of(approver1, 1);
        DocumentApproval documentApproval2 = DocumentApproval.of(approver2, 2);
        DocumentApprovals documentApprovals = new DocumentApprovals();
        documentApprovals.add(documentApproval1);
        documentApprovals.add(documentApproval2);

        String approvalComment1 = "결재 승인합니다.";
        String approvalComment2 = "확인했습니다.";
        documentApprovals.approveBy(approver1, approvalComment1);
        documentApprovals.approveBy(approver2, approvalComment2);

        // when
        boolean isAllApproved = documentApprovals.isAllApproved();

        // then
        assertThat(isAllApproved).isTrue();
    }

    @Test
    @DisplayName("모든 결재자가 승인하지 않았을 경우, 거짓을 반환한다.")
    void isAllApproved_false() {
        // given
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        DocumentApproval documentApproval1 = DocumentApproval.of(approver1, 1);
        DocumentApproval documentApproval2 = DocumentApproval.of(approver2, 2);
        DocumentApprovals documentApprovals = new DocumentApprovals();
        documentApprovals.add(documentApproval1);
        documentApprovals.add(documentApproval2);

        String approvalComment1 = "결재 승인합니다.";
        documentApprovals.approveBy(approver1, approvalComment1);

        // when
        boolean isAllApproved = documentApprovals.isAllApproved();

        // then
        assertThat(isAllApproved).isFalse();
    }

    @Test
    @DisplayName("문서를 결재하는 경우 내가 결재할 차례가 아니면 결재가 실패한다.")
    void approveFail() {
        // given
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        DocumentApproval documentApproval1 = DocumentApproval.of(approver1, 1);
        DocumentApproval documentApproval2 = DocumentApproval.of(approver2, 2);
        DocumentApprovals documentApprovals = new DocumentApprovals();
        documentApprovals.add(documentApproval1);
        documentApprovals.add(documentApproval2);

        // when // then
        assertThrows(IllegalArgumentException.class, () -> documentApprovals.approveBy(approver2, "확인했습니다."));
    }

    @DisplayName("결재 대상 리스트에 내가 없으면 결재가 실패한다.")
    @Test
    void approveFail2() {
        // given
        User approver1 = UserFixture.create(1L, "결재자1");
        User approver2 = UserFixture.create(2L, "결재자2");

        DocumentApproval documentApproval1 = DocumentApproval.of(approver1, 1);
        DocumentApprovals documentApprovals = new DocumentApprovals();
        documentApprovals.add(documentApproval1);

        // when // then
        assertThrows(IllegalArgumentException.class, () -> documentApprovals.approveBy(approver2, "확인했습니다."));
    }
}
