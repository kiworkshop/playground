package learning;

import fixture.DocumentFixture;
import fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DocumentTest {

    @Test
    @DisplayName("문서 생성하기")
    void create() {
        // given
        User drafter = UserFixture.create(1L, "기안자");
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        String title = "문서제목";
        String contents = "문서내용";

        // when
        Document document = DocumentFixture.create(1L, title, Category.EDUCATION, contents, drafter);
        document.addApprovers(Arrays.asList(approver1, approver2));

        // then
        assertThat(document)
                .extracting("title", "category", "contents", "ApprovalState", "drafter.id")
                .containsExactly(title, Category.EDUCATION, contents, ApprovalState.DRAFTING, drafter.getId());

        List<DocumentApproval> documentApprovals = document.getDocumentApprovals();
        assertThat(documentApprovals)
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(
                        tuple(approver1.getId(), ApprovalState.DRAFTING, 1, null),
                        tuple(approver2.getId(), ApprovalState.DRAFTING, 2, null)
                );
    }

    @Test
    @DisplayName("문서를 결재하는 경우 내가 결재할 차례면 결재가 성공한다.")
    void approveSuccess() {
        // given
        User drafter = UserFixture.create(1L, "기안자");
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        Document document = DocumentFixture.create(1L, "문서제목", Category.EDUCATION, "문서내용", drafter);
        document.addApprovers(Arrays.asList(approver1, approver2));

        String approvalComment = "결재 승인합니다.";

        // when
        document.approveBy(approver1, approvalComment);

        // then
        assertThat(document.getApprovalState()).isEqualTo(ApprovalState.DRAFTING);

        List<DocumentApproval> documentApprovals = document.getDocumentApprovals();
        assertThat(documentApprovals)
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(
                        tuple(approver1.getId(), ApprovalState.APPROVED, 1, approvalComment),
                        tuple(approver2.getId(), ApprovalState.DRAFTING, 2, null)
                );
    }

    @Test
    @DisplayName("문서를 결재하는 경우 내가 마지막 결재할 차례라면 문서의 상태가 결재완료로 변경된다.")
    void approveComplete() {
        // given
        User drafter = UserFixture.create(1L, "기안자");
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        Document document = DocumentFixture.create(1L, "문서제목", Category.EDUCATION, "문서내용", drafter);
        document.addApprovers(Arrays.asList(approver1, approver2));

        String approvalComment1 = "결재 승인합니다.";
        document.approveBy(approver1, approvalComment1);

        String approvalComment2 = "확인했습니다.";

        // when
        document.approveBy(approver2, approvalComment2);

        // then
        assertThat(document.getApprovalState()).isEqualTo(ApprovalState.APPROVED);

        List<DocumentApproval> documentApprovals = document.getDocumentApprovals();
        assertThat(documentApprovals)
                .extracting("approver.id", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(
                        tuple(approver1.getId(), ApprovalState.APPROVED, 1, approvalComment1),
                        tuple(approver2.getId(), ApprovalState.APPROVED, 2, approvalComment2)
                );
    }

    @Test
    @DisplayName("문서를 결재하는 경우 내가 결재할 차례가 아니면 결재가 실패한다.")
    void approveFail() {
        // given
        User drafter = UserFixture.create(1L, "기안자");
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        Document document = DocumentFixture.create(1L, "문서제목", Category.EDUCATION, "문서내용", drafter);
        document.addApprovers(Arrays.asList(approver1, approver2));

        // when // then
        assertThrows(IllegalArgumentException.class, () -> document.approveBy(approver2, "확인했습니다."));
    }

    @DisplayName("결재 대상 리스트에 내가 없으면 결재가 실패한다.")
    @Test
    void approveFail2() {
        // given
        User drafter = UserFixture.create(1L, "기안자");
        User approver1 = UserFixture.create(2L, "결재자1");
        User approver2 = UserFixture.create(3L, "결재자2");

        Document document = DocumentFixture.create(1L, "문서제목", Category.EDUCATION, "문서내용", drafter);
        document.addApprovers(Collections.singletonList(approver1));

        // when // then
        assertThrows(IllegalArgumentException.class, () -> document.approveBy(approver2, "확인했습니다."));
    }
}
