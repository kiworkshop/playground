package playground.domain.document;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.user.JobPosition;
import playground.domain.user.User;
import playground.domain.user.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static playground.domain.document.ApprovalState.APPROVED;
import static playground.domain.document.ApprovalState.DRAFTING;
import static playground.domain.document.Category.EDUCATION;
import static playground.domain.user.JobPosition.TEAM_MEMBER;

@SpringBootTest
class DocumentRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        documentRepository.deleteAllInBatch();
    }

    @DisplayName("기안자 id와 결재상태로 문서를 역순 조회한다.")
    @Test
    void findByDrafterIdAndApprovalStateOrderByIdDesc() {
        // given
        User user1 = createUser("wbluke@abc.com", "p@ssw0rd", TEAM_MEMBER);
        User user2 = createUser("wbluke2@abc.com", "p@ssw0rd", TEAM_MEMBER);

        Document document1 = createDocument("title1", EDUCATION, DRAFTING, user1);
        Document document2 = createDocument("title2", EDUCATION, DRAFTING, user1);
        Document document3 = createDocument("title3", EDUCATION, APPROVED, user1);
        Document document4 = createDocument("title4", EDUCATION, DRAFTING, user2);

        // when
        List<Document> documents = documentRepository.findByDrafterIdAndApprovalStateOrderByIdDesc(user1.getId(), DRAFTING);

        // then
        assertThat(documents).hasSize(2)
                .extracting("id", "title")
                .containsExactly(
                        tuple(document2.getId(), document2.getTitle()),
                        tuple(document1.getId(), document1.getTitle())
                );
    }

    @DisplayName("테스트 데이터가 남아있는지 체크")
    @Test
    void test() {
        assertThat(documentRepository.findAll()).hasSize(0);
    }

    private Document createDocument(String title, Category category, ApprovalState approvalState, User drafter) {
        Document document = Document.builder()
                .title(title)
                .category(category)
                .approvalState(approvalState)
                .drafter(drafter)
                .build();
        return documentRepository.save(document);
    }

    private User createUser(String email, String password, JobPosition jobPosition) {
        User user = User.builder()
                .email(email)
                .password(password)
                .jobPosition(jobPosition)
                .build();
        return userRepository.save(user);
    }


}
