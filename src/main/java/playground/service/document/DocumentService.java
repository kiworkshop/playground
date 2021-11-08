package playground.service.document;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.controller.document.request.CreateDocumentRequest;
import playground.domain.document.ApprovalState;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.user.User;
import playground.repository.document.DocumentApprovalRepository;
import playground.repository.document.DocumentRepository;
import playground.service.document.response.SelectDocumentResponse;
import playground.service.document.response.SelectSingleOutBoxResponse;
import playground.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentApprovalRepository documentApprovalRepository;
    private final UserService userService;

    public DocumentService(final DocumentRepository documentRepository,
                           final DocumentApprovalRepository documentApprovalRepository,
                           final UserService userService) {
        this.documentRepository = documentRepository;
        this.documentApprovalRepository = documentApprovalRepository;
        this.userService = userService;
    }

    @Transactional
    public void save(final CreateDocumentRequest createDocumentRequest) {
        Long drafterId = createDocumentRequest.getDrafterId();
        List<Long> approverIds = createDocumentRequest.getApproverIds();
        checkUserExistence(drafterId, approverIds);

        Document document = createDocumentRequest.toDocument();
        Long documentId = documentRepository.save(document);

        List<DocumentApproval> documentApprovals = createDocumentRequest.toDocumentApprovals(documentId);
        documentApprovalRepository.saveAll(documentApprovals);
    }

    private void checkUserExistence(final Long drafterId, final List<Long> approvalIds) {
        ArrayList<Long> userIds = new ArrayList<>();

        if (!approvalIds.contains(drafterId)) {
            userIds.add(drafterId);
        }

        userIds.addAll(approvalIds);

        List<User> users = userService.findAllById(userIds);

        if (userIds.size() != users.size()) {
            throw new IllegalArgumentException("전달받은 회원 식별자와 일치하는 회원을 모두 찾지 못했습니다.");
        }
    }

    @Transactional(readOnly = true)
    public SelectDocumentResponse select(final Long documentId) {
        Document document = documentRepository.findById(documentId);
        User user = userService.findById(document.getDrafterId());

        return new SelectDocumentResponse(document, user);
    }

    @Transactional(readOnly = true)
    public List<SelectSingleOutBoxResponse> selectOutBox(final Long drafterId) {
        List<Document> documents = documentRepository.findAllByDrafterIdAndApprovalState(drafterId, ApprovalState.DRAFTING);
        checkEmpty(documents);

        return documents.stream()
                .map(SelectSingleOutBoxResponse::new)
                .collect(Collectors.toList());
    }

    private void checkEmpty(final List<Document> documents) {
        if (documents.isEmpty()) {
            throw new IllegalArgumentException("현재 결재중인 문서가 존재하지 않습니다.");
        }
    }
}
