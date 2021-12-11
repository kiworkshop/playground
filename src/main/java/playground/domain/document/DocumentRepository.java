package playground.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DocumentRepository extends JpaRepository<Document, Long> {

    // JPQL
    @Query("select d from Document d where d.drafter.id = :drafterId and d.approvalState = :approvalState order by d.id desc")
    List<Document> findByDrafterIdAndApprovalStateOrderByIdDesc(
            @Param("drafterId") Long drafterId,
            @Param("approvalState") ApprovalState approvalState
    );

}
