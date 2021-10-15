package playground.domain.documentapproval;

import fixture.DocumentApprovalFixture;
import fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import playground.domain.document.ApprovalState;
import playground.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class DocumentApprovalTest {

    @Test
    @DisplayName("문서 결재 정보를 생성한다.")
    void create() {
        //given
        User approver = UserFixture.create(1L, "결재자");
        int approvalOrder = 1;

        //when
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, ApprovalState.DRAFTING, 1, null);

        //then
        assertThat(documentApproval)
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(approver.getId(), ApprovalState.DRAFTING, approvalOrder, null);
    }

    @ParameterizedTest
    @EnumSource(value = ApprovalState.class, names = "APPROVED")
    @DisplayName("결재 승인 상태일 경우, 참을 반환한다.")
    void isApproved_true(ApprovalState approvalState) {
        //given
        User approver = UserFixture.create(1L, "결재자");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, approvalState, 1, null);

        //when
        boolean isApproved = documentApproval.isApproved();

        //then
        assertThat(isApproved).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = ApprovalState.class, names = {"DRAFTING", "CANCELED"})
    @DisplayName("결재 승인 상태가 아닐 경우, 거짓을 반환한다.")
    void isApproved_false(ApprovalState approvalState) {
        //given
        User approver = UserFixture.create(1L, "결재자");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, approvalState, 1, null);

        //when
        boolean isApproved = documentApproval.isApproved();

        //then
        assertThat(isApproved).isFalse();
    }

    @ParameterizedTest
    @EnumSource(value = ApprovalState.class, names = {"DRAFTING", "CANCELED"})
    @DisplayName("결재가 승인 상태가 아닐 경우, 참을 반환한다.")
    void isNotApproved_true(ApprovalState approvalState) {
        //given
        User approver = UserFixture.create(1L, "결재자");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, approvalState, 1, null);

        //when
        boolean isNotApproved = documentApproval.isNotApproved();

        //then
        assertThat(isNotApproved).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = ApprovalState.class, names = {"APPROVED"})
    @DisplayName("결재가 승인 상태일 경우, 거짓을 반환한다.")
    void isNotApproved_false(ApprovalState approvalState) {
        //given
        User approver = UserFixture.create(1L, "결재자");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, approvalState, 1, null);

        //when
        boolean isNotApproved = documentApproval.isNotApproved();

        //then
        assertThat(isNotApproved).isFalse();
    }

    @Test
    @DisplayName("전달받은 사용자와 등록된 결재자가 다른 지 확인한다.")
    void hasNotSameApprover() {
        //given
        User approver = UserFixture.create(1L, "결재자");
        User notApprover = UserFixture.create(2L, "결재자아님");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, ApprovalState.DRAFTING, 1, null);

        //when, then
        assertThat(documentApproval.hasNotSameApprover(notApprover)).isTrue();
        assertThat(documentApproval.hasNotSameApprover(approver)).isFalse();
    }

    @Test
    @DisplayName("결재 상태를 승인 상태로 변경한다.")
    void changeStateToApproved() {
        //given
        User approver = UserFixture.create(1L, "결재자");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, ApprovalState.DRAFTING, 1, null);
        String approvalComment = "결재 승인하였습니다.";

        //when
        documentApproval.changeStateToApproved(approvalComment);

        //then
        assertAll(
                () -> assertThat(documentApproval.getApprovalState()).isEqualTo(ApprovalState.APPROVED),
                () -> assertThat(documentApproval.getApprovalComment()).isEqualTo(approvalComment)
        );
    }

    @Test
    @DisplayName("순서가 일치할 경우, 참을 반환한다.")
    void isSameOrder() {
        //given
        User approver = UserFixture.create(1L, "결재자");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, ApprovalState.DRAFTING, 1, null);

        //when
        boolean isLastApproval = documentApproval.isSameOrder(1);

        //then
        assertThat(isLastApproval).isTrue();
    }

    @Test
    @DisplayName("순서가 일치하지 않을 경우, 거짓을 반환한다.")
    void isSameOrder_false() {
        //given
        User approver = UserFixture.create(1L, "결재자");
        DocumentApproval documentApproval = DocumentApprovalFixture.create(approver, ApprovalState.DRAFTING, 1, null);

        //when
        boolean isLastApproval = documentApproval.isSameOrder(2);

        //then
        assertThat(isLastApproval).isFalse();
    }
}
