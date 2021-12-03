package playground.service.document;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.domain.user.*;
import playground.service.document.dto.DocumentRequest;
import playground.service.document.dto.DocumentResponse;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static playground.domain.document.Category.EDUCATION;
import static playground.domain.document.approval.ApprovalState.DRAFTING;
import static playground.domain.user.JobPosition.*;

@SpringBootTest
class DocumentServiceTest {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    private User dafter;
    private User approver1;
    private User approver2;

    @BeforeEach
    void setUp() {
        Team team = teamRepository.save(new Team("새로운팀"));
        dafter = userRepository.save(createUser("user1", "pa@sw**d", "user1@gmail.com", team, TEAM_MEMBER));
        approver1 = userRepository.save(createUser("user2", "pa@sw**d", "user2@gmail.com", team, TEAM_LEADER));
        approver2 = userRepository.save(createUser("user3", "pa@sw**d", "user3@gmail.com", team, PART_MANAGER));
    }

    @AfterEach
    public void cleanUp() {
        teamRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("문서 단건 조회")
    void findOne() {
        // given
        DocumentRequest request = DocumentRequest.builder()
                .title("문서 제목")
                .category(EDUCATION)
                .contents("제출합니다.")
                .drafterId(dafter.getId())
                .approverIds(Arrays.asList(approver1.getId(), approver2.getId()))
                .build();

        DocumentResponse document = documentService.save(request);

        // when
        DocumentResponse response = documentService.findOne(document.getId());

        // then
        assertThat(response)
                .extracting("title", "contents", "drafter.name", "category", "approvalState")
                .containsExactly("문서 제목", "제출합니다.", "user1", EDUCATION, DRAFTING);
        assertThat(response.getApprovers())
                .extracting("approverTeamName", "approverName", "approvalOrder", "approvalStateText")
                .containsExactly(tuple("새로운팀", "user2", 1, "결재중"),
                        tuple("새로운팀", "user3", 2, "결재중"));
    }

    private User createUser(String name, String password, String email, Team team, JobPosition jobPosition) {
        return User.builder()
                .name(name)
                .password(password)
                .email(email)
                .team(team)
                .jobPosition(jobPosition)
                .build();
    }
}
