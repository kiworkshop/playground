package playground.domain.document.dao;

import playground.domain.document.dto.AddDocumentParam;
import playground.domain.document.entity.Document;

public interface DocumentDao {

    Document findById(Long id);

    Long addDocument(AddDocumentParam addDocumentParam);
}
