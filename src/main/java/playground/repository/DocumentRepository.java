package playground.repository;

import learning.Document;
import learning.DocumentApproval;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository {

    Optional<Document> findById(Long documentId);

    List<DocumentApproval> findOutBox(Long userId);

    Document save(Document document);
}
