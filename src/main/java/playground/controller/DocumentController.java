package playground.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.dto.CategoryResponse;
import playground.dto.DocumentOutboxResponse;
import playground.dto.DocumentRequest;
import playground.dto.DocumentResponse;
import playground.service.DocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable Long documentId) {
        DocumentResponse DocumentResponse = documentService.findDocumentBy(documentId);
        return ResponseEntity.ok().body(DocumentResponse);
    }

    @GetMapping("/outbox")
    public ResponseEntity<List<DocumentOutboxResponse>> getDocumentsOutbox(@RequestParam Long userId) {
        List<DocumentOutboxResponse> DocumentOutboxResponse = documentService.findDocumentsOutbox(userId);
        return ResponseEntity.ok().body(DocumentOutboxResponse);
    }

    @PostMapping
    public ResponseEntity<Long> insertDocument(@RequestBody DocumentRequest documentRequest) {
        Long id = documentService.insertDocument(documentRequest);
        return ResponseEntity.ok().body(id);
    }
    @GetMapping("/categories")
    public List<CategoryResponse> getCategories(){
        return documentService.getCategories();
    }
}
