package playground.domain.document;

import playground.domain.document.dto.AddDocumentRequest;
import playground.domain.document.dto.OutBox;
import playground.domain.document.dto.SingleDocument;

public interface DocumentService {

    SingleDocument findById(Long id);

    Long addDocument(AddDocumentRequest addDocumentRequest);

    OutBox findOutboxOf(Long drafterId);
}
