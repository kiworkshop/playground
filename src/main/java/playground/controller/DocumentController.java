package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentRequest;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxResponse;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentResponse> find(@PathVariable Long id) {
        DocumentResponse document = documentService.findOne(id);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/documents/out")
    public ResponseEntity<List<OutboxResponse>> list(@RequestParam("userId") Long userId) {
        List<OutboxResponse> outBox = documentService.findOutBox(userId);
        return ResponseEntity.ok(outBox);
    }

    @PostMapping("/document")
    public ResponseEntity<DocumentResponse> save(@RequestBody DocumentRequest requestDto) {
        DocumentResponse document = documentService.save(requestDto);
        return ResponseEntity.created(URI.create("/documents/" + document.getId())).body(document);
    }
}
