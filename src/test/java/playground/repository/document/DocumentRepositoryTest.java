package playground.repository.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;

import static org.assertj.core.api.Assertions.assertThat;

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
}
