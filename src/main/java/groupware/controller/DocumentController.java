package groupware.controller;

import groupware.dto.DocumentDto;
import groupware.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//        - 문서 단건 조회
//        - OUTBOX 문서 리스트 조회
//        - 문서 생성 요청
@RestController
public class DocumentController {
    @Autowired
    DocumentService documentService;

    @GetMapping("/document/{documentId}")
    public DocumentDto getDocumentById(@PathVariable Long documentId) {
        return documentService.findDocumentBy(documentId);
    }
}
