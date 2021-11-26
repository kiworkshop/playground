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
import playground.domain.document.DocumentApproval;
import playground.web.document.dto.OutboxDocumentRequest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static playground.common.type.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentApplication {

    private final DocumentService documentService;
    private final UserService userService;

    public void create(DocumentCreateRequest request) {
        User drafter = userService.findById(request.getDrafterId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다.")); // TODO: 2021/11/26 UserNotFoundException 
        Document document = request.toEntity(drafter);

        List<User> approvers = userService.findAllById(request.getApproverIds());
        IntStream.range(0, approvers.size())
            .mapToObj(index -> DocumentApproval.builder()
                .approver(approvers.get(index))
                .approvalState(DRAFTING)
                .approvalOrder(index + 1)
                .build()
            )
            .forEach(document::addDocumentApproval);

        documentService.createDocument(document);
    }

    public DocumentResponse findDocument(Long documentId) {
        Document document = documentService.findById(documentId)
            .orElseThrow(() -> new IllegalArgumentException()); // TODO: 2021/11/19 DocumentNotFoundException
        return new DocumentResponse(document);
    }

    public List<OutboxDocumentResponse> findOutboxDocuments(OutboxDocumentRequest request) {
        List<Document> documents = documentService.findAllByDrafterIdAndApprovalState(request.getDrafterId(), DRAFTING);

        return convertOutboxDocumentResponseFrom(documents);
    }

    private List<OutboxDocumentResponse> convertOutboxDocumentResponseFrom(List<Document> documents) {
        return documents.stream()
            .map(OutboxDocumentResponse::new)
            .sorted(Comparator.comparing(OutboxDocumentResponse::getId))
            .collect(Collectors.toList());
    }

}
