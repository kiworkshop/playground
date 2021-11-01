package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.dto.DocumentRequest;
import playground.dto.DocumentResponse;
import playground.dto.OutboxResponse;
import playground.service.DocumentService;

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
        return ResponseEntity.ok(document);
    }
}
