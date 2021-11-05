package groupware.service;

import groupware.dao.DocumentDao;
import groupware.domain.Document;
import groupware.dto.DocumentResponse;
import groupware.dto.DocumentOutboxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentService {
    @Autowired
    DocumentDao documentDao;

    public DocumentResponse findDocumentBy(Long documentId) {
        Document document = documentDao.findById(documentId);
        return new DocumentResponse(document);
    }

    public List<DocumentOutboxResponse> findDocumentsOutbox(Long userId) {
        List<Document> documents = documentDao.findDocumentsOutbox(userId);
        List<DocumentOutboxResponse> documentOutboxDtos = new ArrayList<>();
        for (int i = 0; i < documents.size(); i++) {
            documentOutboxDtos.add(new DocumentOutboxResponse(documents.get(i)));
        }
        return  documentOutboxDtos;
    }
}
