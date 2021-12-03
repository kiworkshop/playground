package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.common.type.ApprovalState;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Transactional
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    public Optional<Document> findById(Long documentId) {
        return documentRepository.findById(documentId);
    }

    public List<Document> findAllByDrafterIdAndApprovalStateOrderByIdDesc(Long drafterId, ApprovalState approvalState) {
        return documentRepository.findByDrafterIdAndApprovalStateOrderByIdDesc(drafterId, approvalState);
    }
}
