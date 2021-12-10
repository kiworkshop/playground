package playground.web.document.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.user.Team;
import playground.domain.user.User;
import playground.service.document.DocumentService;
import playground.web.document.api.request.OutboxDocumentRequest;
import playground.web.document.api.response.DocumentApprovalResponse;
import playground.web.document.api.response.DocumentResponse;
import playground.web.document.api.response.OutboxDocumentResponse;
import playground.web.user.api.response.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

import static playground.domain.document.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentSearchApplication {

    private final DocumentService documentService;

    public DocumentResponse findDocument(Long documentId) {
        Document document = documentService.getById(documentId);
        UserResponse drafter = convertUserResponseFrom(document.getDrafter());
        List<DocumentApprovalResponse> approvers = convertDocumentApprovalResponse(document.getDocumentApprovals());

        return new DocumentResponse(document, drafter, approvers);
    }

    private UserResponse convertUserResponseFrom(User user) {
        Team team = user.getTeam();
        return new UserResponse(user, team);
    }

    private List<DocumentApprovalResponse> convertDocumentApprovalResponse(List<DocumentApproval> documentApprovals) {
        return documentApprovals.stream()
            .map(documentApproval -> {
                User approver = documentApproval.getApprover();
                Team team = approver.getTeam();
                return new DocumentApprovalResponse(documentApproval, approver, team);
            })
            .collect(Collectors.toList());
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
