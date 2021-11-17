package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.domain.document.approval.ApprovalRepository;
import playground.domain.document.approval.DocumentApproval;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.document.dto.DocumentRequest;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DocumentService {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final ApprovalRepository approvalRepository;

    public DocumentResponse findOne(Long documentId) {
        Optional<Document> document = documentRepository.findById(documentId);
        return DocumentResponse.convertFrom(document.orElseGet(Document::new));
    }

    public List<OutboxResponse> findOutBox(Long userId) {
        List<Document> outBox = documentRepository.findOutBox(userId);
        return outBox.stream()
                .map(OutboxResponse::convertFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public DocumentResponse save(DocumentRequest dto) {
        User drafter = findUser(dto.getDrafterId());
        Document document = dto.toDocument(drafter);

        int approvalOrder = 0;
        for (Long approvalId : dto.getApproverIds()) {
            User approver = ifExistApprover(userRepository.findById(approvalId));
            DocumentApproval documentApproval = DocumentApproval.create(approver, approvalOrder++);
            document.addDocumentApprovals(documentApproval);
        }

        documentRepository.save(document);
        return DocumentResponse.convertFrom(document);
    }

    private User findUser(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        return findUser.orElseThrow(() -> new IllegalStateException("문서 기안 사용자를 찾을 수 없습니다."));
    }

    private User ifExistApprover(Optional<User> approver) {
        if (!approver.isPresent()) {
            throw new IllegalStateException("문서 결재 사용자를 찾을 수 없습니다.");
        }
        return approver.get();
    }
}
