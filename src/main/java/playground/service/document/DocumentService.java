package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.domain.document.ApprovalState;

import java.util.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Transactional
    public Document create(Document document) {
        return documentRepository.save(document);
    }

    public Optional<Document> findById(Long documentId) {
        return documentRepository.findById(documentId);
    }

    public Document getById(Long documentId) {
        return findById(documentId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 문서입니다. documentId = %s", documentId)));
    }

    public List<Document> findAllByDrafterIdAndApprovalStateOrderByIdDesc(Long drafterId, ApprovalState approvalState) {
        return documentRepository.findByDrafterIdAndApprovalStateOrderByIdDesc(drafterId, approvalState);
    }

    public List<Category> findAllDocumentCategories() {
        Category[] categories = Category.values();
        return Arrays.asList(categories);
    }
}
