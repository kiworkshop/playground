package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.domain.user.User;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentTest {

    @Test
    @DisplayName("문서를 생성한다.")
    void create() {
        //given
        String title = "교육비 결재";
        Category category = Category.EDUCATION;
        String contents = "교육비";
        User drafter = new User("test@naver.com", "Password123!", "drafter");

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
