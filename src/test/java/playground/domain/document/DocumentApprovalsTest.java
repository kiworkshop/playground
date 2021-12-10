package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import playground.domain.document.vo.Category;
import playground.domain.team.Team;
import playground.domain.user.User;
import playground.domain.user.vo.JobPosition;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.mockito.Mockito.mock;

class DocumentApprovalsTest {

    @Test
    @DisplayName("결재자를 등록한다.")
    void enroll() {
        //given
        Team team = mock(Team.class);
        User drafter = User.builder()
                .email("test1@naver.com")
                .password("Password123!")
                .name("drafter")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

        User approver1 = User.builder()
                .email("test2@naver.com")
                .password("Password123!")
                .name("approver1")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

        User approver2 = User.builder()
                .email("test2@naver.com")
                .password("Password123!")
                .name("approver2")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

        Document document = Document.builder()
                .drafter(drafter)
                .category(Category.EDUCATION)
                .title("교육비 정산")
                .contents("교육비 정산 결재")
                .build();

        DocumentApprovals documentApprovals = new DocumentApprovals();

        //when
        documentApprovals.enroll(Arrays.asList(approver1, approver2), document);

        //then
        assertThat(documentApprovals.getDocumentApprovals()).hasSize(2);
    }

    @Test
    @DisplayName("이미 결재자가 등록된 상태에서, 결재자를 추가할 경우 예외가 발생한다.")
    void enroll_fail() {
        //given
        Team team = mock(Team.class);
        User drafter = User.builder()
                .email("test1@naver.com")
                .password("Password123!")
                .name("drafter")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();
        User approver1 = User.builder()
                .email("test2@naver.com")
                .password("Password123!")
                .name("approver1")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();
        User approver2 = User.builder()
                .email("test2@naver.com")
                .password("Password123!")
                .name("approver2")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

        Document document = Document.builder()
                .drafter(drafter)
                .category(Category.EDUCATION)
                .title("교육비 정산")
                .contents("교육비 정산 결재")
                .build();

        DocumentApprovals documentApprovals = new DocumentApprovals();
        documentApprovals.enroll(Arrays.asList(approver1, approver2), document);
        User approver3 = User.builder()
                .email("test4@naver.com")
                .password("Password123!")
                .name("approver3")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

        //when, then
        assertThatIllegalStateException()
                .isThrownBy(() -> documentApprovals.enroll(Collections.singletonList(approver3), document))
                .withMessage("결재자 추가 등록이 불가능합니다.");
    }
}
