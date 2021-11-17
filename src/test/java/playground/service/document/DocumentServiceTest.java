package playground.service.document;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.service.document.dto.DocumentRequest;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Test
    void findById() {
        //given //when
        DocumentResponse document = documentService.findOne(1L);

        //then
        assertThat(document).extracting("title", "contents", "category", "approvalState", "userName")
                .containsExactly(
                        document.getTitle(),
                        document.getContents(),
                        document.getCategory(),
                        document.getApprovalState(),
                        document.getUserName()
                );
    }

    @Test
    void findOutBoxBy() {
        //given //when
        List<OutboxResponse> outBox = documentService.findOutBox(1L);

        //then
        assertThat(outBox.size()).isEqualTo(3);
        assertThat(outBox).extracting("title", "approvalState")
                .containsExactly(
                        tuple("문서1", ApprovalState.DRAFTING),
                        tuple("문서2", ApprovalState.DRAFTING),
                        tuple("문서3", ApprovalState.DRAFTING)
                );
    }

    @Test
    void save() {
        //given
        DocumentRequest requestDto = DocumentRequest.builder()
                .title("문서100")
                .category(Category.PRODUCT_PURCHASING)
                .contents("제출합니다.")
                .drafterId(1L)
                .approverIds(Arrays.asList(1L, 2L, 3L))
                .build();

        //when
        DocumentResponse result = documentService.save(requestDto);

        //then
        assertThat(result.getTitle()).isEqualTo("문서100");
    }
}