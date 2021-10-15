package playground.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.domain.Document;

@RestController(value = "api")
public class DocumentController {

    @RequestMapping("/documents/{documentId}")
    public ResponseEntity<Document> getDocument(@PathVariable int documentId) {

        return null;
    }
}
