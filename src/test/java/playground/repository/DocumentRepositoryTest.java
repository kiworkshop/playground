package playground.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import playground.domain.Document;
import playground.domain.Team;
import playground.domain.User;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.domain.Category.EDUCATION;
import static playground.domain.Category.PRODUCT_PURCHASING;
import static playground.domain.Rank.TEAM_MEMBER;

@DataJpaTest
public class DocumentRepositoryTest {

    @Autowired
    private TeamRepositiory teamRepositiory;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @DisplayName("기안자 id의 모든 문서를 작성일 역순 조회한다.")
    @Test
    void findByDrafterIdAndOrderByCreatedDateDesc() {
        // given
        Team team =  teamRepositiory.save(new Team("서비스개발팀"));
        User user1 = userRepository.save(new User("김우빈", "wbluke@abc.com", "p@ssw0rd", TEAM_MEMBER, team));
        User user2 = userRepository.save(new User("김우빈2", "wbluke2@abc.com", "p@ssw0rd", TEAM_MEMBER, team));

        Document document1 = new Document("title1", EDUCATION, "content", user1);
        Document document2 = new Document("title2", PRODUCT_PURCHASING, "content", user1);
        Document document3 = new Document("title3", EDUCATION, "content", user1);
        Document document4 = new Document("title4", PRODUCT_PURCHASING, "content", user2);
        documentRepository.saveAll(Arrays.asList(document1, document2, document3, document4));

        // when
        List<Document> documents = documentRepository.findByDrafterIdOrderByCreatedDateDesc(user1.getId());

        // then
        assertThat(documents).hasSize(3)
                .extracting("title")
                .containsExactly("title3", "title2", "title1");
    }

}
