package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.service.document.dto.DocumentResponse;
import playground.service.document.dto.OutboxDocumentResponse;
import playground.web.document.dto.DocumentRequest;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.user.User;
import playground.service.user.UserService;
import playground.web.document.dto.OutboxDocumentRequest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static playground.type.ApprovalState.DRAFTING;

@RequiredArgsConstructor
@Service
public class DocumentApplication {

    private static DocumentService documentService;
    private static DocumentApprovalService documentApprovalService;
    private static UserService userService;

    public void createDocument(DocumentRequest request) {
        Document document = this.covertFrom(request);
        Long documentId = documentService.createDocument(document);

        List<Long> approverIds = request.getApproverIds();
        IntStream.range(0, approverIds.size())
            .forEach(
                index -> {
                    DocumentApproval documentApproval = DocumentApproval.builder()
                        .approverId(approverIds.get(index))
                        .documentId(documentId)
                        .approvalState(DRAFTING)
                        .approvalOrder(index + 1)
                        .build();
                    documentApprovalService.create(documentApproval);
                }
            );
    }

    private Document covertFrom(DocumentRequest request) {
        return Document.builder()
            .title(request.getTitle())
            .category(request.getCategory())
            .contents(request.getContents())
            .drafterId(request.getDrafterId())
            .approvalState(DRAFTING)
            .build();
    }

    @Transactional(readOnly = true)
    public DocumentResponse getDocument(Long documentId) {
        Document document = documentService.getDocument(documentId);
        User user = userService.getUser(document.getDrafterId());

        return DocumentResponse.from(document, user);
    }

    @Transactional(readOnly = true)
    public List<OutboxDocumentResponse> listOutboxDocuments(OutboxDocumentRequest request) {
        List<Document> documents = documentService.listDocumentsByUserId(drafterId);

        return documents.stream()
            .map(OutboxDocumentResponse::from)
            .sorted(Comparator.comparing(OutboxDocumentResponse::getId))
            .collect(Collectors.toList());
    }
}
