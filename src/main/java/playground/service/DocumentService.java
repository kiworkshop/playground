package playground.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.controller.request.CreateDocumentRequest;
import playground.domain.Document;
import playground.domain.DocumentApproval;
import playground.domain.User;
import playground.repository.DocumentApprovalRepository;
import playground.repository.DocumentRepository;

import java.util.ArrayList;
import java.util.List;

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
        userIds.add(drafterId);
        userIds.addAll(approvalIds);

        List<User> users = userService.findAllById(userIds);

        if (userIds.size() != users.size()) {
            throw new IllegalArgumentException("전달받은 회원 식별자와 일치하는 회원을 모두 찾지 못했습니다.");
        }
    }
}
