package groupware.controller;

import groupware.dto.DocumentResponse;
import groupware.dto.DocumentOutboxResponse;
import groupware.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//        - 문서 단건 조회
//        - OUTBOX 문서 리스트 조회
//        - 문서 생성 요청
@RestController
@RequestMapping("/api")
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/document/{documentId}")
    public ResponseEntity<DocumentResponse> getDocumentById(@PathVariable Long documentId) {
        DocumentResponse documentDto = documentService.findDocumentBy(documentId);
        return new ResponseEntity<DocumentResponse>(documentDto, HttpStatus.OK);
    }

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentOutboxResponse>> getDocumentsOutbox(@RequestParam Long userId) {
        List<DocumentOutboxResponse> DocumentOutboxDtos = documentService.findDocumentsOutbox(userId);
        return new ResponseEntity<List<DocumentOutboxResponse>>(DocumentOutboxDtos, HttpStatus.OK);
    }
}
