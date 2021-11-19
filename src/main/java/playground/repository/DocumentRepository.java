package playground.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import playground.domain.Document;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByDrafterId(Long userId);
}
