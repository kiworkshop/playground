package groupware.service;

import groupware.dao.DocumentDao;
import groupware.domain.Document;
import groupware.dto.DocumentDto;
import groupware.dto.DocumentOutboxDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentService {
    @Autowired
    DocumentDao documentDao;

    public DocumentDto findDocumentBy(Long documentId) {
        Document document = documentDao.findById(documentId);
        return new DocumentDto(document);
    }

    public List<DocumentOutboxDto> findDocumentsOutbox(Long userId) {
        List<Document> documents = documentDao.findDocumentsOutbox(userId);
        List<DocumentOutboxDto> documentOutboxDtos = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            documentOutboxDtos.add(new DocumentOutboxDto(documents.get(i)));
        }
        return  documentOutboxDtos;
    }
}
