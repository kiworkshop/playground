package playground.service;

import learning.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.dto.DocumentRequest;
import playground.dto.DocumentResponse;
import playground.dto.OutboxResponse;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

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
        assertThat(outBox).extracting("id", "title", "approvalState")
                .containsExactly(
                        tuple(outBox.get(0).getId(), outBox.get(0).getTitle(), outBox.get(0).getApprovalState()),
                        tuple(outBox.get(1).getId(), outBox.get(1).getTitle(), outBox.get(1).getApprovalState()),
                        tuple(outBox.get(2).getId(), outBox.get(2).getTitle(), outBox.get(2).getApprovalState())
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