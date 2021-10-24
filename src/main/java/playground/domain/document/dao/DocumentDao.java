package playground.domain.document.dao;

import playground.domain.document.dto.param.AddDocumentParam;
import playground.domain.document.entity.Document;

import java.util.List;

public interface DocumentDao {

    Document findById(Long id);

    Long addDocument(AddDocumentParam addDocumentParam);

    List<Document> findByDrafter(Long drafterId);
}
