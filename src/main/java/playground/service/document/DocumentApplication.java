package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.user.User;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxDocumentResponse;
import playground.service.user.UserService;
import playground.web.document.dto.DocumentCreateRequest;
import playground.domain.document.Document;
import playground.web.document.dto.OutboxDocumentRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static playground.domain.document.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentApplication {

    private final DocumentService documentService;
    private final UserService userService;

    public void create(DocumentCreateRequest request) {
        List<User> orderedApprovers = createOrderedApprovers(request);
        User drafter = findUserById(request.getDrafterId());

        Document document = request.toEntity(drafter);
        document.createApprovals(orderedApprovers);

        documentService.createDocument(document);
    }

    private List<User> createOrderedApprovers(DocumentCreateRequest request) {
        Map<Long, User> approversById = createApproverMap(request);

        List<User> orderedApprovers = new ArrayList<>();
        for (Long approverId : request.getApproverIds()) {
            orderedApprovers.add(approversById.get(approverId));
        }
        return orderedApprovers;
    }

    private Map<Long, User> createApproverMap(DocumentCreateRequest request) {
        List<User> approvers = userService.findAllById(request.getApproverIds());
        return approvers.stream()
            .collect(Collectors.toMap(User::getId, user -> user));
    }

    private User findUserById(Long userId) {
        return userService.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 사용자입니다. userId = %s", userId)));
    }

    public DocumentResponse findDocument(Long documentId) {
        Document document = findDocumentById(documentId);
        User drafter = document.getDrafter();

        return new DocumentResponse(document, drafter);
    }

    private Document findDocumentById(Long documentId) {
        return documentService.findById(documentId)
            .orElseThrow(() -> new IllegalArgumentException(String.format("존재하지 않는 문서입니다. documentId = %s", documentId)));
    }

    public List<OutboxDocumentResponse> findOutboxDocuments(OutboxDocumentRequest request) {
        List<Document> documents = documentService.findAllByDrafterIdAndApprovalStateOrderByIdDesc(request.getDrafterId(), DRAFTING);
        return convertOutboxDocumentResponseFrom(documents);
    }

    private List<OutboxDocumentResponse> convertOutboxDocumentResponseFrom(List<Document> documents) {
        return documents.stream()
            .map(OutboxDocumentResponse::new)
            .collect(Collectors.toList());
    }

}
