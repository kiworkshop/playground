package playground.document.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playground.document.entity.Document;
import playground.document.entity.DocumentRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public Long createDocument(Document document) {
        return documentRepository.save(document);
    }

    public Document getDocument(Long documentId) {
        return documentRepository.findById(documentId);
    }

    public List<Document> listOutboxDocuments(Long drafterId) {
        return null;
    }
}
