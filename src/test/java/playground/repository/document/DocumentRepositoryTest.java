package playground.repository.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@SpringBootTest
@Transactional
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    @DisplayName("문서를 저장한다.")
    void save() {
        //given
        Document document = Document.builder()
                .title("교육비 결재")
                .category("EDUCATION")
                .contents("교육비")
                .drafterId(1L)
                .build();

        //when
        long documentId = documentRepository.save(document);

        //then
        assertThat(documentId).isNotZero();
    }

    @Test
    @DisplayName("문서를 조회한다.")
    void findById() {
        //given
        String title = "교육비 결재";
        String category = "EDUCATION";
        String contents = "교육비";
        long drafterId = 1L;

        Document document = Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafterId(drafterId)
                .build();
        long documentId = documentRepository.save(document);

        //when
        Document fetchedDocument = documentRepository.findById(documentId);

        //then
        assertThat(fetchedDocument)
                .extracting("title", "category", "contents", "drafterId", "approvalState")
                .containsExactly(title, Category.valueOf(category), contents, drafterId, ApprovalState.DRAFTING);
    }

    @Test
    @DisplayName("식별번호에 일치하는 문서가 존재하지 않을 경우, 예외가 발생한다.")
    void findById_fail_empty_result() {
        //when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> documentRepository.findById(0L))
                .withMessageContaining("해당하는 문서가 존재하지 않습니다.");
    }
}
