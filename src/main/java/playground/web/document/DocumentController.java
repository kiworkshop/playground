package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.web.document.dto.DocumentRequest;
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
    public ResponseEntity<Void> createDocument(@RequestBody DocumentRequest requestDto) {
        documentApplication.createDocument(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/api/documents/{documentId}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable Long documentId) {
        DocumentResponse documentResponseDto = documentApplication.getDocument(documentId);
        return ResponseEntity.ok(documentResponseDto);
    }

    @GetMapping(path = "/api/documents/outbox")
    public ResponseEntity<List<OutboxDocumentResponse>> listOutboxDocuments(@RequestBody OutboxDocumentRequest requestDto) {
        List<OutboxDocumentResponse> outboxDocumentDtos = documentApplication.listOutboxDocuments(requestDto);
        return ResponseEntity.ok(outboxDocumentDtos);
    }
}
