package groupware.controller;

import groupware.dto.DocumentOutboxResponse;
import groupware.dto.DocumentRequest;
import groupware.dto.DocumentResponse;
import groupware.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/document/{documentId}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable Long documentId) {
        DocumentResponse documentDto = documentService.findDocumentBy(documentId);
        return new ResponseEntity<>(documentDto, HttpStatus.OK);
    }

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentOutboxResponse>> getDocumentsOutbox(@RequestParam Long userId) {
        List<DocumentOutboxResponse> documentOutboxDtos = documentService.findDocumentsOutbox(userId);
        return new ResponseEntity<>(documentOutboxDtos, HttpStatus.OK);
    }

    @PostMapping("/document")
    public ResponseEntity<Long> insertDocument(@RequestBody DocumentRequest documentRequest){
        Long id = documentService.insertDocument(documentRequest);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
