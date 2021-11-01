package playground.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.dto.DocumentDto;
import playground.service.DocumentService;

import java.util.List;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentDto> find(@PathVariable Long id) {
        DocumentDto document = documentService.findOne(id);
        return ResponseEntity.ok(document);
    }

    @GetMapping("/documents/out")
    public ResponseEntity<List<DocumentDto>> list(@RequestParam("userId") Long userId) {
        List<DocumentDto> outBox = documentService.findOutBox(userId);
        return ResponseEntity.ok(outBox);
    }

    @PostMapping("/document")
    public ResponseEntity<DocumentDto> save(@RequestBody DocumentDto document) {
        DocumentDto result = documentService.save(document);
        return ResponseEntity.ok(result);
    }
}
