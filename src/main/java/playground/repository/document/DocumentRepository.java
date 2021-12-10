package playground.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import playground.domain.document.Document;
import playground.domain.document.vo.ApprovalState;
import playground.service.document.response.SelectCategoryResponse;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("select d from Document d join fetch d.drafter join fetch d.documentApprovals.documentApprovals where d.id = :documentId")
    Optional<Document> findDocumentAndDrafterAndDocumentApprovalsById(@Param("documentId") final Long documentId);

    @Query("select d from Document d join fetch d.drafter where d.drafter.id = :documentId and d.approvalState = :approvalState")
    List<Document> findAllDocumentAndDrafterByDrafterIdAndApprovalState(@Param("documentId") final Long drafterId, @Param("approvalState") final ApprovalState approvalState);

    @Query("select distinct new playground.service.document.response.SelectCategoryResponse(d.category) from Document d")
    List<SelectCategoryResponse> findCategories();
}
