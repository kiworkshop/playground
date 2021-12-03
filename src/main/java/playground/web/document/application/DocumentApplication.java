package playground.web.document.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.user.User;
import playground.service.document.DocumentService;
import playground.service.user.UserService;
import playground.web.document.api.request.DocumentCreateRequest;
import playground.web.document.api.request.OutboxDocumentRequest;
import playground.web.document.api.response.DocumentResponse;
import playground.web.document.api.response.OutboxDocumentResponse;

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

    @Transactional
    public void create(DocumentCreateRequest request) {
        List<User> orderedApprovers = createOrderedApprovers(request);
        User drafter = userService.getById(request.getDrafterId());

        Document document = request.toEntity(drafter);
        document.createApprovals(orderedApprovers);

        documentService.create(document);
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

    public DocumentResponse findDocument(Long documentId) {
        Document document = documentService.getById(documentId);
        User drafter = document.getDrafter();

        return new DocumentResponse(document, drafter);
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
