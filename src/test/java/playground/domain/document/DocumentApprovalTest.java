package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.domain.user.vo.JobPosition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DocumentApprovalTest {

    @Test
    @DisplayName("결재 정보를 저장한다.")
    void create() {
        //given
        Team team = mock(Team.class);
        User drafter = User.builder()
                .email("test1@naver.com")
                .password("Password123!")
                .name("drafter")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

        User approver = User.builder()
                .email("test2@naver.com")
                .password("Password123!")
                .name("approver")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

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
