package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.web.document.dto.DocumentCreateRequest;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxDocumentResponse;
import playground.service.document.DocumentApplication;
import playground.web.document.dto.OutboxDocumentRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentApplication documentApplication;

    @PostMapping(path = "/api/documents")
    public ResponseEntity<Void> createDocument(@RequestBody DocumentCreateRequest requestDto) {
        documentApplication.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/api/documents/{documentId}")
    public ResponseEntity<DocumentResponse> findDocument(@PathVariable Long documentId) {
        DocumentResponse documentResponseDto = documentApplication.findDocument(documentId);
        return ResponseEntity.ok(documentResponseDto);
    }

    @GetMapping(path = "/api/documents/outbox")
    public ResponseEntity<List<OutboxDocumentResponse>> findOutboxDocuments(OutboxDocumentRequest requestDto) {
        List<OutboxDocumentResponse> outboxDocumentDtos = documentApplication.findOutboxDocuments(requestDto);
        return ResponseEntity.ok(outboxDocumentDtos);
    }
}
