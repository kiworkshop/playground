package playground.service;

import playground.dao.DocumentDao;
import playground.dao.UserDao;
import playground.domain.Document;
import playground.domain.User;
import playground.dto.DocumentRequest;
import playground.dto.DocumentResponse;
import playground.dto.DocumentOutboxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentService {
    @Autowired
    DocumentDao documentDao;

    @Autowired
    UserDao userDao;

    public DocumentResponse findDocumentBy(Long documentId) {
        Document document = documentDao.findById(documentId);
        User user = userDao.findById(document.getDrafter().getId());
        return new DocumentResponse(document, user.getName());
    }

    public List<DocumentOutboxResponse> findDocumentsOutbox(Long userId) {
        List<Document> documents = documentDao.findDocumentsOutbox(userId);
        List<DocumentOutboxResponse> documentOutboxDtos = new ArrayList<>();
        for (Document document : documents) {
            documentOutboxDtos.add(new DocumentOutboxResponse(document));
        }
        return  documentOutboxDtos;
    }

    public long insertDocument(DocumentRequest documentRequest) {
        return documentDao.insertDocument(documentRequest);
    }
}
