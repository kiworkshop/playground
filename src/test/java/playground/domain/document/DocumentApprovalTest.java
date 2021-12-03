package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.domain.team.Team;
import playground.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DocumentApprovalTest {

    @Test
    @DisplayName("결재 정보를 저장한다.")
    void create() {
        //given
        Team team = mock(Team.class);
        User drafter = new User("test@naver.com", "Password123!", "drafter", team);
        User approver = new User("test@naver.com", "Password123!", "approver", team);
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
