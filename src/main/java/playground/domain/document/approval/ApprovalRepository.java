package playground.domain.document.approval;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<DocumentApproval, Long> {

}
