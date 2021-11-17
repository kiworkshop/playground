package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.ApprovalRepository;
import playground.domain.document.Document;
import playground.domain.document.DocumentApprovals;
import playground.domain.document.DocumentRepository;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.document.dto.DocumentRequest;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxResponse;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final ApprovalRepository approvalRepository;

    public DocumentResponse findOne(Long documentId) {
        Document document = documentRepository.findById(documentId);
        return DocumentResponse.convertFrom(document);
    }

    public List<OutboxResponse> findOutBox(Long userId) {
        List<Document> outBox = documentRepository.findOutBox(userId);
        return outBox.stream()
                .map(OutboxResponse::convertFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentResponse save(DocumentRequest dto) {
        User drafter = userRepository.findById(dto.getDrafterId());

        Document document = dto.toDocument(drafter);
        Long documentId = documentRepository.save(document);

        DocumentApprovals approvals = DocumentApprovals.create(dto.getApproverIds(), documentId, drafter);
        approvalRepository.saveAll(approvals);

        return DocumentResponse.convertFrom(document);
    }
}
