package playground.service.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.domain.document.approval.DocumentApproval;
import playground.domain.user.User;
import playground.domain.user.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static playground.domain.document.approval.ApprovalState.DRAFTING;

@DataJpaTest
class DocumentServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;

    private User user1;
    private User user2;

    private Document document;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(new User("user1"));
        user2 = userRepository.save(new User("user2"));

        document = documentRepository.save(createDocument("문서 제목", "제출합니다."));
    }

    @Test
    void findById() {
        //when
        Optional<Document> findDocument = documentRepository.findById(document.getId());

        //then
        assertThat(findDocument).hasValueSatisfying(actual ->
                assertThat(actual)
                        .extracting("title", "contents", "category", "approvalState")
                        .containsExactly(document.getTitle(), document.getContents(), document.getCategory(), document.getApprovalState())
        );
    }

    @Test
    void findOutBoxBy() {
        //given
        document.addDocumentApprovals(DocumentApproval.create(user1, 1));
        document.addDocumentApprovals(DocumentApproval.create(user2, 2));

        //when
        List<Document> outBox = documentRepository.findOutBox(user2.getId());

        //then
        assertThat(outBox.size()).isEqualTo(1);
        assertThat(outBox).extracting("title", "approvalState")
                .containsExactly(
                        tuple("문서 제목", DRAFTING)
                );
    }

    private Document createDocument(String title, String contents) {
        return Document.builder()
                .drafter(user1)
                .title(title)
                .category(Category.PRODUCT_PURCHASING)
                .contents(contents)
                .build();
    }
}