package playground.web.document.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.web.document.api.request.DocumentCreateRequest;
import playground.web.document.api.response.DocumentResponse;
import playground.web.document.api.response.OutboxDocumentResponse;
import playground.web.document.application.DocumentCreateApplication;
import playground.web.document.api.request.OutboxDocumentRequest;
import playground.web.document.application.DocumentSearchApplication;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentCreateApplication documentCreateApplication;
    private final DocumentSearchApplication documentSearchApplication;

    @PostMapping(path = "/api/documents")
    public ResponseEntity<Void> createDocument(@RequestBody DocumentCreateRequest requestDto) {
        documentCreateApplication.create(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/api/documents/{documentId}")
    public ResponseEntity<DocumentResponse> findDocument(@PathVariable Long documentId) {
        DocumentResponse documentResponseDto = documentSearchApplication.findDocument(documentId);
        return ResponseEntity.ok(documentResponseDto);
    }

    @GetMapping(path = "/api/documents/outbox")
    public ResponseEntity<List<OutboxDocumentResponse>> findOutboxDocuments(OutboxDocumentRequest requestDto) {
        List<OutboxDocumentResponse> outboxDocumentDtos = documentSearchApplication.findOutboxDocuments(requestDto);
        return ResponseEntity.ok(outboxDocumentDtos);
    }

}
