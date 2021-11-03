package playground.domain.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentTest {

    @Test
    @DisplayName("문서를 생성한다.")
    void create() {
        //given
        String title = "교육비 결재";
        String category = "EDUCATION";
        String contents = "교육비";
        long drafterId = 1L;

        //when
        Document document = Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafterId(drafterId)
                .build();

        //then
        assertThat(document)
                .extracting("title", "category", "contents", "drafterId", "approvalState")
                .containsExactly(title, Category.valueOf(category), contents, drafterId, ApprovalState.DRAFTING);
    }
}
