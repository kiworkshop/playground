package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.type.ApprovalState;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findDocumentsByDrafterIdAndApprovalState(Long drafterId, ApprovalState approvalState);
}
