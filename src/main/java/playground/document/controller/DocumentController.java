package playground.document.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.document.controller.dto.DocumentRequest;
import playground.document.controller.dto.DocumentResponse;
import playground.document.controller.dto.OutboxDocumentResponse;
import playground.document.service.DocumentApplication;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentApplication documentApplication;

    @PostMapping(path = "/api/documents")
    public ResponseEntity<Void> createDocument(@RequestBody DocumentRequest documentRequest) {
        documentApplication.createDocument(documentRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/api/documents/{documentId}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable Long documentId) {
        DocumentResponse response = documentApplication.getDocument(documentId);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/api/documents/outbox")
    public ResponseEntity<List<OutboxDocumentResponse>> listOutboxDocuments(@RequestParam Long drafterId) {
        List<OutboxDocumentResponse> response = documentApplication.listOutboxDocuments(drafterId);
        return ResponseEntity.ok(response);
    }
}
