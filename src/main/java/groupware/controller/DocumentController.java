package groupware.controller;

import groupware.dto.DocumentDto;
import groupware.dto.DocumentOutboxDto;
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
    public ResponseEntity<DocumentDto> getDocumentById(@PathVariable Long documentId) {
        DocumentDto documentDto = documentService.findDocumentBy(documentId);
        return new ResponseEntity<DocumentDto>(documentDto, HttpStatus.OK);
    }

    @GetMapping("/documents/outbox")
    public ResponseEntity<List<DocumentOutboxDto>> getDocumentsOutbox(@RequestParam Long userId) {
        List<DocumentOutboxDto> DocumentOutboxDtos = documentService.findDocumentsOutbox(userId);
        return new ResponseEntity<List<DocumentOutboxDto>>(DocumentOutboxDtos, HttpStatus.OK);
    }
}
