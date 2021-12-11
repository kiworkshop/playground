package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.dao.document.dto.DocumentTitleResponse;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.document.DocumentRepository;
import playground.domain.user.User;
import playground.domain.user.UserRepository;
import playground.service.document.dto.DocumentApprovalResponse;
import playground.service.document.dto.DocumentResponse;
import playground.web.document.dto.DocumentCreateRequest;
import playground.web.document.dto.DocumentOutboxRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static playground.domain.document.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public List<DocumentTitleResponse> findOutboxDocuments(DocumentOutboxRequest request) {
        List<Document> outboxDocuments = documentRepository.findByDrafterIdAndApprovalStateOrderByIdDesc(request.getDrafterId(), DRAFTING);
        return convertTitleDtoFrom(outboxDocuments);
    }

    public DocumentResponse findDocument(Long documentId) {
        Document document = findDocumentById(documentId);

        return new DocumentResponse(document);
    }

    @Transactional
    public void create(DocumentCreateRequest request) {
        List<User> orderedApprovers = createOrderedApprovers(request);
        User drafter = findUserById(request.getDrafterId());

        Document document = request.toEntity(drafter, orderedApprovers);
        documentRepository.save(document);
    }

    private List<DocumentTitleResponse> convertTitleDtoFrom(List<Document> documents) {
        return documents.stream()
                .map(DocumentTitleResponse::new)
                .collect(Collectors.toList());
    }

    private Document findDocumentById(Long documentId) {
        return documentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 문서입니다. documentId = %s", documentId)));
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 사용자입니다. userId = %s", userId)));
    }

    private List<User> createOrderedApprovers(DocumentCreateRequest request) {
        Map<Long, User> approverMap = createApproverMap(request);

        List<User> orderedApprovers = new ArrayList<>();
        for (Long approverId : request.getApproverIds()) {
            orderedApprovers.add(approverMap.get(approverId));
        }
        return orderedApprovers;
    }

    private Map<Long, User> createApproverMap(DocumentCreateRequest request) {
        List<User> approvers = userRepository.findAllById(request.getApproverIds());
        return approvers.stream()
                .collect(Collectors.toMap(User::getId, user -> user));
    }

}
