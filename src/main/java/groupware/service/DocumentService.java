package groupware.service;

import groupware.dao.DocumentDao;
import groupware.domain.Document;
import groupware.dto.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentService {
    @Autowired
    DocumentDao documentDao;

    public DocumentDto findDocumentBy(Long documentId) {
        Document document = documentDao.findById(documentId);
        return new DocumentDto(document);
    }
}
