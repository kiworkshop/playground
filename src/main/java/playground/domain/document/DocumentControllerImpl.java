package playground.domain.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playground.domain.document.dto.AddDocumentRequest;
import playground.domain.document.dto.BoxDocument;
import playground.domain.document.dto.OutBox;
import playground.domain.document.dto.SingleDocument;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final DocumentService documentService;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SingleDocument> findDocument(@PathVariable Long id) {
        SingleDocument result = documentService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> addDocument(@RequestBody AddDocumentRequest addDocumentRequest) {
        Long documentId = documentService.addDocument(addDocumentRequest);

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(documentId);
    }

    @Override
    @GetMapping("/outbox")
    public ResponseEntity<List<BoxDocument>> findOutbox(@RequestParam Long drafterId) {
        OutBox outbox = documentService.findOutboxOf(drafterId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(outbox.getElements());
    }
}
