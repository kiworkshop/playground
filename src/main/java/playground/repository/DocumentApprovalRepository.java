package playground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.Document;
import playground.domain.DocumentApproval;

import java.util.List;

public interface DocumentApprovalRepository extends JpaRepository<DocumentApproval, Long> {

    List<DocumentApproval> findByDocument(Document document);
}
