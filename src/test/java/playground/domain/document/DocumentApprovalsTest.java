package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import playground.domain.document.vo.Category;
import playground.domain.team.Team;
import playground.domain.user.User;

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
        User drafter = new User("test1@naver.com", "Password123!", "drafter", team);
        User approver1 = new User("test2@naver.com", "Password123!", "approver1", team);
        User approver2 = new User("test3@naver.com", "Password123!", "approver2", team);

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
        User drafter = new User("test1@naver.com", "Password123!", "drafter", team);
        User approver1 = new User("test2@naver.com", "Password123!", "approver1", team);
        User approver2 = new User("test3@naver.com", "Password123!", "approver2", team);

        Document document = Document.builder()
                .drafter(drafter)
                .category(Category.EDUCATION)
                .title("교육비 정산")
                .contents("교육비 정산 결재")
                .build();

        DocumentApprovals documentApprovals = new DocumentApprovals();
        documentApprovals.enroll(Arrays.asList(approver1, approver2), document);
        User approver3 = new User("test4@naver.com", "Password123!", "approver3", team);

        //when, then
        assertThatIllegalStateException()
                .isThrownBy(() -> documentApprovals.enroll(Collections.singletonList(approver3), document))
                .withMessage("결재자 추가 등록이 불가능합니다.");
    }
}
