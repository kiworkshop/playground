package playground.document.entity;

public interface DocumentRepository {

    Long save(Document document);
    Document findById(Long documentId);
}
