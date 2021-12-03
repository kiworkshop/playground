package playground.repository.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import playground.domain.document.DocumentApproval;

import java.util.List;

public interface DocumentApprovalRepository extends JpaRepository<DocumentApproval, Long> {

    @Query("select d from DocumentApproval d join fetch d.approver a join fetch a.team where d.id in :documentApprovalIds")
    List<DocumentApproval> findAllDocumentApprovalAndApproverAndTeamByIds(@Param("documentApprovalIds") List<Long> documentApprovalIds);
}
