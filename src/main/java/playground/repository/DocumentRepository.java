package playground.repository;

import learning.Document;

import java.util.List;

public interface DocumentRepository {

    Document findById(Long documentId);

    List<Document> findOutBox(Long userId);

    Document save(Document document);
}
