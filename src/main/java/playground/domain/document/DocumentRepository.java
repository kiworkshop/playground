package playground.domain.document;

import java.util.List;

public interface DocumentRepository {

    Document findById(Long documentId);

    List<Document> findOutBox(Long userId);

    Long save(Document document);
}
