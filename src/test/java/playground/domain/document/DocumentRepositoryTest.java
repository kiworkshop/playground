package playground.domain.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import playground.domain.user.User;
import playground.domain.user.UserRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static playground.domain.document.approval.ApprovalState.DRAFTING;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DocumentRepository documentRepository;

    private User user1;
    private User user2;

    private Document document;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(createUser("user1", "pa@sw**d", "user1@gmail.com"));
        user2 = userRepository.save(createUser("user2", "pa@sw**d", "user2@gmail.com"));

        document = documentRepository.save(createDocument("문서 제목", "제출합니다."));
    }

    @Test
    void findOutBoxBy() {
        //given
        List<User> approvers = Arrays.asList(user1, user2);
        document.createApprovals(approvers);

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

    private User createUser(String name, String password, String email) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .build();
    }
}