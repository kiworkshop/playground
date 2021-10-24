package playground.domain.document;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import playground.domain.document.dto.AddDocumentRequest;
import playground.domain.document.dto.DocumentDto;

@RequestMapping(path = "/api/documents",
        produces = MediaType.APPLICATION_JSON_VALUE)
public interface DocumentController {

    ResponseEntity<DocumentDto> findDocument(Long id);

    ResponseEntity<Long> addDocument(AddDocumentRequest addDocumentRequest);
}
