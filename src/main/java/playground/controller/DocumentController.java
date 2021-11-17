package playground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.dto.DocumentOutboxResponse;
import playground.dto.DocumentRequest;
import playground.dto.DocumentResponse;
import playground.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/document/{documentId}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable Long documentId) {
        DocumentResponse documentDto = documentService.findDocumentBy(documentId);
        return ResponseEntity.ok().body(documentDto);
    }

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentOutboxResponse>> getDocumentsOutbox(@RequestParam Long userId) {
        List<DocumentOutboxResponse> documentOutboxDtos = documentService.findDocumentsOutbox(userId);
        return ResponseEntity.ok().body(documentOutboxDtos);
    }

    @PostMapping("/document")
    public ResponseEntity<Long> insertDocument(@RequestBody DocumentRequest documentRequest) {
        Long id = documentService.insertDocument(documentRequest);
        return ResponseEntity.ok().body(id);
    }
}
