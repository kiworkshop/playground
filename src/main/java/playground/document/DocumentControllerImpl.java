package playground.document;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import playground.document.dto.DocumentDto;

@RestController
@RequiredArgsConstructor
public class DocumentControllerImpl implements DocumentController {

    private final DocumentService documentService;

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<DocumentDto> findDocument(@PathVariable Long id) {
        DocumentDto result = documentService.findById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);
    }
}
