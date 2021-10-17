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
        DocumentDto document = documentService.findOne(1L);

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
        List<DocumentDto> outBox = documentService.findOutBox(1L);

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
        DocumentDto dto = DocumentDto.builder()
                .title("문서100")
                .contents("제출합니다.")
                .category(Category.PRODUCT_PURCHASING.name())
                .userId(1L)
                .build();

        //when
        DocumentDto result = documentService.save(dto);

        //then
        assertThat(result.getTitle()).isEqualTo("문서100");
    }
}