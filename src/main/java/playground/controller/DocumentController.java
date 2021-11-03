package playground.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.controller.request.CreateDocumentRequest;
import playground.service.DocumentService;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(final DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CreateDocumentRequest createDocumentRequest) {
        documentService.save(createDocumentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
