package playground.domain.document;

import java.util.List;

public interface DocumentRepository {

    Long save(Document document);
    Document findById(Long documentId);
    List<Document> findAllByUserId(Long drafterId);
}
