package playground.domain.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.domain.document.dto.AddDocumentRequest;
import playground.domain.document.dto.DocumentDto;

@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final DocumentService documentService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> findDocument(@PathVariable Long id) {
        DocumentDto result = documentService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addDocument(@RequestBody AddDocumentRequest addDocumentRequest) {
        Long documentId = documentService.addDocument(addDocumentRequest);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(documentId);
    }
}
