package playground.controller;

import learning.Document;
import learning.DocumentApproval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.dto.DocumentDto;
import playground.service.DocumentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<Document> find(@PathVariable Long id) {
        Optional<Document> document = documentService.findOne(id);

        return document.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/documents/out/{userId}")
    public ResponseEntity<List<DocumentApproval>> list(@PathVariable("userId") Long userId) {
        List<DocumentApproval> outBox = documentService.findOutBox(userId);
        return new ResponseEntity<>(outBox, HttpStatus.OK);
    }

    @PostMapping("/document")
    public ResponseEntity<DocumentDto> save(@RequestBody DocumentDto document) {
        DocumentDto result = documentService.save(document);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
