package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import playground.common.type.ApprovalState;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    // JPQL
    @Query("select d from Document d where d.drafter.id = :drafterId and d.approvalState = :approvalState order by d.id desc")
    List<Document> findByDrafterIdAndApprovalStateOrderByIdDesc(
        @Param("drafterId") Long drafterId,
        @Param("approvalState") ApprovalState approvalState
    );

    //List<Document> findDocumentsByDrafterIdAndApprovalState(Long drafterId, ApprovalState approvalState);
}
