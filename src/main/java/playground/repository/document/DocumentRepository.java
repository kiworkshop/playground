package playground.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import playground.domain.document.Document;
import playground.domain.document.vo.ApprovalState;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("select d from Document d join fetch d.drafter where d.id = :document_id")
    Optional<Document> findDocumentAndDrafterById(@Param("document_id") final Long documentId);

    @Query("select d from Document d join fetch d.drafter where d.drafter.id = :drafter_id and d.approvalState = :approval_state")
    List<Document> findAllDocumentAndDrafterByDrafterIdAndApprovalState(@Param("drafter_id") final Long drafterId, @Param("approval_state") final ApprovalState approvalState);
}
