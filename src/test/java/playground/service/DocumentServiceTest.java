package playground.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.Document;
import playground.domain.Team;
import playground.domain.User;
import playground.dto.DocumentResponse;
import playground.repository.DocumentRepository;
import playground.repository.TeamRepositiory;
import playground.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static playground.domain.Category.EDUCATION;
import static playground.domain.Rank.TEAM_MEMBER;

@SpringBootTest
public class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private TeamRepositiory teamRepositiory;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        documentRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        teamRepositiory.deleteAllInBatch();
    }

    @DisplayName("id로 Document 단건을 조회한다")
    @Test
    void fetchDocumentById() {
        // given
        Team team = teamRepositiory.save(new Team("서비스개발팀"));
        User user = userRepository.save(new User("김우빈", "wbluke@abc.com", "p@ssw0rd", TEAM_MEMBER, team));
        Document document = new Document("제목", EDUCATION, "내용", user); // private method
        Document savedDocument = documentRepository.save(document);

        // when
        DocumentResponse result = documentService.findDocumentBy(savedDocument.getId());

        // then
        assertThat(result)
                .extracting("title", "contents", "category", "drafter.name")
                .containsExactly("제목", "내용", "EDUCATION", "김우빈");
    }
}
