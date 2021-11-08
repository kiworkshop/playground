package playground.repository.document;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.user.User;
import playground.repository.user.UserRepository;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DocumentApprovalRepositoryTest {

    @Autowired
    private DocumentApprovalRepository documentApprovalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    @DisplayName("결재 정보를 저장한다.")
    void saveAll() {
        //given
        User drafter = new User("drafter@naver.com", "Password123!", "기안자");
        User approver1 = new User("approver1@naver.com", "Password123!", "김성빈1");
        User approver2 = new User("approver2@naver.com", "Password123!", "김성빈2");
        Long drafterId = userRepository.save(drafter);
        Long approverId1 = userRepository.save(approver1);
        Long approverId2 = userRepository.save(approver2);

        Document document = Document.builder()
                .title("교육비 결재")
                .drafterId(drafterId)
                .category("EDUCATION")
                .contents("교육비")
                .build();
        Long documentId = documentRepository.save(document);

        ArrayList<DocumentApproval> documentApprovals = new ArrayList<>();
        documentApprovals.add(DocumentApproval.of(approverId1, documentId, 1));
        documentApprovals.add(DocumentApproval.of(approverId2, documentId, 2));

        //when
        documentApprovalRepository.saveAll(documentApprovals);

        //then
        assertThat(documentApprovalRepository.findAll()).hasSize(2);
    }
}
