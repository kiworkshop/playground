package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.controller.dto.DocumentRequest;
import playground.controller.dto.DocumentResponse;
import playground.controller.dto.OutboxDocumentResponse;
import playground.service.DocumentService;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping(path = "/api/documents")
    public ResponseEntity<Void> createDocument(@RequestBody DocumentRequest documentRequest) {
        documentService.createDocument(documentRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/api/documents/{documentId}")
    public DocumentResponse getDocument(@PathVariable Long documentId) {
        return documentService.getDocument(documentId);
    }

    @GetMapping(path = "/api/documents/outbox")
    public List<OutboxDocumentResponse> listOutboxDocuments(@RequestParam Long drafterId) {
        return documentService.listOutboxDocuments(drafterId);
    }
}