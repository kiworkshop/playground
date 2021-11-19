package playground.document.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.document.entity.Document;
import playground.document.entity.DocumentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Transactional(readOnly = false)
    public Long createDocument(Document document) {
        return documentRepository.save(document);
    }

    @Transactional(readOnly = true)
    public Document getDocument(Long documentId) {
        return documentRepository.findById(documentId);
    }

    @Transactional(readOnly = true)
    public List<Document> listDocumentsByUserId(Long userId) {
        return documentRepository.findAllByUserId(userId);
    }
}
