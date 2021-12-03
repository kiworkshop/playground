package playground.repository.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import playground.domain.document.Document;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.domain.team.Team;
import playground.domain.user.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private PersistenceUnitUtil persistenceUnitUtil;

    @BeforeEach
    void setUp() {
        persistenceUnitUtil = entityManagerFactory.getPersistenceUnitUtil();
    }

    @Test
    @DisplayName("기안자의 정보가 포함된 문서를 조회한다.")
    void findByIdWithDrafter() {
        //given
        Team team = new Team("정산시스템팀");
        entityManager.persist(team);
        User drafter = new User("test@naver.com", "Password123!", "drafter", team);
        entityManager.persist(drafter);
        Document document = Document.builder()
                .drafter(drafter)
                .category(Category.EDUCATION)
                .title("교육비 정산")
                .contents("교육비 정산 결재")
                .build();
        documentRepository.save(document);
        entityManager.flush();
        entityManager.clear();

        //when
        Optional<Document> fetchedDocument = documentRepository.findByIdWithDrafter(document.getId());

        //then
        assertThat(fetchedDocument).isNotEmpty();
        assertThat(persistenceUnitUtil.isLoaded(fetchedDocument.get().getDrafter())).isTrue();
    }

    @Test
    @DisplayName("기안자의 정보가 포함된 조건에 맞는 모든 문서를 조회한다.")
    void findAllWithDrafter() {
        //given
        Team team = new Team("정산시스템팀");
        entityManager.persist(team);
        User drafter = new User("test@naver.com", "Password123!", "drafter", team);
        entityManager.persist(drafter);
        Document document = Document.builder()
                .drafter(drafter)
                .category(Category.EDUCATION)
                .title("교육비 정산")
                .contents("교육비 정산 결재")
                .build();
        documentRepository.save(document);
        entityManager.flush();
        entityManager.clear();

        //when
        List<Document> documents = documentRepository.findAllWithDrafter(drafter.getId(), ApprovalState.DRAFTING);

        //then
        assertThat(documents).hasSize(1);
        assertThat(persistenceUnitUtil.isLoaded(documents.get(0).getDrafter())).isTrue();
    }
}
