package playground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.ApprovalState;
import playground.domain.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByDrafterIdOrderByCreatedDateDesc(Long id);
}
