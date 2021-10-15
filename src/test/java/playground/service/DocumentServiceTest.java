package playground.service;

import learning.Category;
import learning.Document;
import learning.DocumentApproval;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.dto.DocumentDto;

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
        Document document = documentService.findOne(1L).get();

        //then
        assertThat(document).extracting("title", "contents", "category", "approvalState", "drafter.name")
                .containsExactly(
                        document.getTitle(),
                        document.getContents(),
                        document.getCategory(),
                        document.getApprovalState(),
                        document.getDrafter().getName()
                );
    }

    @Test
    void findOutBoxBy() {
        //given //when
        List<DocumentApproval> outBox = documentService.findOutBox(5L);

        //then
        assertThat(outBox.size()).isEqualTo(3);
        assertThat(outBox).extracting("approver.name", "approvalState", "approvalOrder")
                .containsExactly(
                        tuple(outBox.get(0).getApprover().getName(), outBox.get(0).getApprovalState(), outBox.get(0).getApprovalOrder()),
                        tuple(outBox.get(1).getApprover().getName(), outBox.get(1).getApprovalState(), outBox.get(1).getApprovalOrder()),
                        tuple(outBox.get(2).getApprover().getName(), outBox.get(2).getApprovalState(), outBox.get(2).getApprovalOrder())
                );
    }

    @Test
    void save() {
        //given
        DocumentDto dto = DocumentDto.builder()
                .title("문서100")
                .contents("제출합니다.")
                .category(Category.PRODUCT_PURCHASING.getText())
                .drafterId(1L)
                .build();

        //when
        DocumentDto result = documentService.save(dto);

        //then
        assertThat(result.getTitle()).isEqualTo("문서100");
    }
}