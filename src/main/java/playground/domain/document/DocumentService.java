package playground.domain.document;

import playground.domain.document.dto.AddDocumentRequest;
import playground.domain.document.dto.DocumentDto;

public interface DocumentService {

    DocumentDto findById(Long id);

    Long addDocument(AddDocumentRequest addDocumentRequest);
}
