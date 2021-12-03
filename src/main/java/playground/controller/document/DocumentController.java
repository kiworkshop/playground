package playground.controller.document;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import playground.service.document.DocumentService;
import playground.service.document.request.CreateDocumentRequest;
import playground.service.document.response.SelectDocumentResponse;
import playground.service.document.response.SelectSingleOutBoxResponse;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CreateDocumentRequest createDocumentRequest) {
        documentService.create(createDocumentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<SelectDocumentResponse> find(final @PathVariable Long documentId) {
        SelectDocumentResponse selectDocumentResponse = documentService.find(documentId);
        return ResponseEntity.ok(selectDocumentResponse);
    }

    @GetMapping("/outbox")
    public ResponseEntity<List<SelectSingleOutBoxResponse>> findOutBox(final @RequestParam Long drafterId) {
        List<SelectSingleOutBoxResponse> selectMultiOutBoxResponse = documentService.findOutBox(drafterId);
        return ResponseEntity.ok(selectMultiOutBoxResponse);
    }
}
