package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentRepository;
import playground.domain.document.approval.DocumentApproval;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.exception.NotFoundException;
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

    public DocumentResponse findOne(Long documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new NotFoundException("문서를 찾을 수 없습니다."));
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
        User drafter = findUser(dto.getDrafterId());
        Document document = dto.toDocument(drafter);

        int approvalOrder = 0;
        for (Long approvalId : dto.getApproverIds()) {
            User approver = findApprover(approvalId);
            DocumentApproval documentApproval = DocumentApproval.create(approver, approvalOrder++);
            document.addDocumentApprovals(documentApproval);
        }

        documentRepository.save(document);
        return DocumentResponse.convertFrom(document);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("문서 기안 사용자를 찾을 수 없습니다."));
    }

    private User findApprover(Long approvalId) {
        return userRepository.findById(approvalId)
                .orElseThrow(() -> new NotFoundException("문서 결재 사용자를 찾을 수 없습니다."));
    }
}
