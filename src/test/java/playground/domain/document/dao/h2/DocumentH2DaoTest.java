package playground.domain.document.dao.h2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import playground.domain.document.dto.param.AddDocumentParam;
import playground.domain.document.entity.Document;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class DocumentH2DaoTest {

    @Autowired
    private DocumentH2Dao documentH2Dao;

    @Test
    @DisplayName("식별자로 문서를 찾아온다.")
    void find_by_id_success() {
        // given
        Document expected = Document.builder()
                .id(1L)
                .build();

        // when
        Document result = documentH2Dao.findById(expected.getId());

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("존재하지 않는 문서를 조회하면 예외가 발생한다.")
    void find_by_id_fail_no_such_document() {
        // given
        Long id = 999999999L;

        // when, then
        assertThatExceptionOfType(EmptyResultDataAccessException.class)
                .isThrownBy(() -> documentH2Dao.findById(id));
    }

    @Test
    @DisplayName("새 문서를 저장한다.")
    void add_document_success() {
        // given
        AddDocumentParam addDocumentParam = AddDocumentParam.builder()
                .title("title")
                .contents("content")
                .drafterId(1L)
                .categoryText("category")
                .approvalStateText("DRAFTING")
                .build();

        // when
        Long documentId = documentH2Dao.addDocument(addDocumentParam);

        // then 
        assertThat(documentId).isNotNull();
    }

    @Test
    @DisplayName("작성자 ID로 문서를 찾아온다.")
    void find_by_drafter_success() {
        // given
        Long drafterId = 1L;

        // when
        List<Document> documents = documentH2Dao.findByDrafter(drafterId);

        // then
        assertThat(documents)
                .extracting("drafter.id")
                .containsOnly(drafterId);
    }

    @Test
    @DisplayName("작성자가 작성한 문서가 없으면 빈 리스트를 반환한다.")
    void find_by_drafter_success_empty() {
        // given
        Long drafterId = 2L;

        // when
        List<Document> documents = documentH2Dao.findByDrafter(drafterId);

        // then
        assertThat(documents).isEmpty();
    }
}