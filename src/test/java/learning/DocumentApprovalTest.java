package learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DocumentApprovalTest {

    @Test
    @DisplayName("문서 결재 정보를 생성한다.")
    void create() {
        //given
        User approver = new User(1L, "결재자");
        int approvalOrder = 1;

        //when
        DocumentApproval documentApproval = createDocumentApproval(approver, ApprovalState.DRAFTING, 1, null);

        //then
        assertThat(documentApproval)
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(approver.getId(), ApprovalState.DRAFTING, approvalOrder, null);
    }

    @ParameterizedTest
    @CsvSource(value = {"APPROVED, true", "DRAFTING, false", "CANCELED, false"})
    @DisplayName("결재 승인 상태인지 확인한다.")
    void isApproved(ApprovalState approvalState, boolean expected) {
        //given
        User approver = new User(1L, "결재자");
        DocumentApproval documentApproval = createDocumentApproval(approver, approvalState, 1, null);

        //when
        boolean isApproved = documentApproval.isApproved();

        //then
        assertThat(isApproved).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"APPROVED, false", "DRAFTING, true", "CANCELED, true"})
    @DisplayName("결재가 승인 상태가 아닌지 확인한다.")
    void isNotApproved(ApprovalState approvalState, boolean expected) {
        //given
        User approver = new User(1L, "결재자");
        DocumentApproval documentApproval = createDocumentApproval(approver, approvalState, 1, null);

        //when
        boolean isNotApproved = documentApproval.isNotApproved();

        //then
        assertThat(isNotApproved).isEqualTo(expected);
    }

    @Test
    @DisplayName("전달받은 사용자와 등록된 결재자가 다른 지 확인한다.")
    void hasNotSameApprover() {
        //given
        User approver = new User(1L, "결재자");
        User notApprover = new User(2L, "결재자아님");
        DocumentApproval documentApproval = createDocumentApproval(approver, ApprovalState.DRAFTING, 1, null);

        //when, then
        assertThat(documentApproval.hasNotSameApprover(notApprover)).isTrue();
        assertThat(documentApproval.hasNotSameApprover(approver)).isFalse();
    }

    @Test
    @DisplayName("결재 상태를 승인 상태로 변경한다.")
    void changeStateToApproved() {
        //given
        User approver = new User(1L, "결재자");
        DocumentApproval documentApproval = createDocumentApproval(approver, ApprovalState.DRAFTING, 1, null);
        String approvalComment = "결재 승인하였습니다.";

        //when
        documentApproval.changeStateToApproved(approvalComment);

        //then
        assertAll(
                () -> assertThat(documentApproval.getApprovalState()).isEqualTo(ApprovalState.APPROVED),
                () -> assertThat(documentApproval.getApprovalComment()).isEqualTo(approvalComment)
        );
    }

    @ParameterizedTest
    @CsvSource(value = {"1, true", "2, false"})
    @DisplayName("순서가 일치하는지 확인한다.")
    void isSameOrder(int approvalOrder, boolean expected) {
        //given
        User approver = new User(1L, "결재자");
        DocumentApproval documentApproval = createDocumentApproval(approver, ApprovalState.DRAFTING, 1, null);

        //when
        boolean isLastApproval = documentApproval.isSameOrder(approvalOrder);

        //then
        assertThat(isLastApproval).isEqualTo(expected);
    }

    private DocumentApproval createDocumentApproval(final User approver, final ApprovalState approvalState,
                                                    final Integer approvalOrder, final String approvalComment) {
        return DocumentApproval.builder()
                .approver(approver)
                .approvalState(approvalState)
                .approvalOrder(approvalOrder)
                .approvalComment(approvalComment)
                .build();
    }
}
