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

class DocumentTest {

    @Test
    @DisplayName("문서를 생성한다.")
    void create() {
        //given
        String title = "교육비 결재";
        Category category = Category.EDUCATION;
        String contents = "교육비";
        Team team = mock(Team.class);
        User drafter = User.builder()
                .email("test@naver.com")
                .password("Password123!")
                .name("drafter")
                .team(team)
                .jobPosition(JobPosition.TEAM_MEMBER)
                .build();

        //when
        Document document = Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafter(drafter)
                .build();

        //then
        assertThat(document)
                .extracting("title", "category", "contents", "drafter", "approvalState")
                .containsExactly(title, category, contents, drafter, ApprovalState.DRAFTING);
    }
}
