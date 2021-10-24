package playground.domain.document.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentTest {

    @Test
    @DisplayName("식별자로 동등성을 비교한다.")
    void equality_with_id() {
        // given
        Long id = 1L;
        Document document_one = Document.builder()
                .id(id)
                .title("1번 문서")
                .build();
        Document document_two = Document.builder()
                .id(id)
                .title("제목이 달라요")
                .build();

        // when
        boolean result = document_one.equals(document_two);

        // then
        assertThat(result).isTrue();
    }
}