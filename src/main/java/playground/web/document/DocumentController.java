package playground.web.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import playground.dao.document.dto.DocumentTitleResponse;
import playground.service.document.DocumentService;
import playground.service.document.dto.DocumentResponseDto;
import playground.web.document.dto.DocumentCreateRequest;
import playground.web.document.dto.DocumentOutboxRequest;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/api/documents/outbox")
    public ResponseEntity<List<DocumentTitleResponse>> findOutboxDocuments(DocumentOutboxRequest requestDto) {
        List<DocumentTitleResponse> outboxDocumentDtos = documentService.findOutboxDocuments(requestDto);
        return ResponseEntity.ok(outboxDocumentDtos);
    }

    @GetMapping("/api/documents/{documentId}")
    public ResponseEntity<DocumentResponseDto> findDocument(@PathVariable Long documentId) {
        DocumentResponseDto documentResponseDto = documentService.findDocument(documentId);
        return ResponseEntity.ok(documentResponseDto);
    }

    @PostMapping("/api/documents")
    public ResponseEntity<Object> createDocument(@RequestBody DocumentCreateRequest requestDto) {
        documentService.create(requestDto);
        return ResponseEntity.ok().build();
    }

}
