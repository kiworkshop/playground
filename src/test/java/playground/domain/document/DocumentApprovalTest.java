package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentApprovalTest {

    @Test
    @DisplayName("결재 정보를 저장한다.")
    void create() {
        //given
        User drafter = new User("test@naver.com", "Password123!", "drafter");
        User approver = new User("test@naver.com", "Password123!", "approver");
        Document document = Document.builder()
                .drafter(drafter)
                .category(Category.EDUCATION)
                .title("교육비 정산")
                .contents("교육비 정산 결재")
                .build();
        int approvalOrder = 1;

        //when
        DocumentApproval documentApproval = DocumentApproval.of(approver, document, approvalOrder);

        //then
        assertThat(documentApproval)
                .extracting("approver", "document", "approvalState", "approvalOrder", "approvalComment")
                .containsExactly(approver, document, ApprovalState.DRAFTING, approvalOrder, null);
    }
}
