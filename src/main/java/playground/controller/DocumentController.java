package playground.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.controller.request.CreateDocumentRequest;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/documents")
public class DocumentController {

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid final CreateDocumentRequest createDocumentRequest) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
