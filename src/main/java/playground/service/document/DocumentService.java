package playground.service.document;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.document.vo.ApprovalState;
import playground.domain.user.User;
import playground.repository.document.DocumentApprovalRepository;
import playground.repository.document.DocumentRepository;
import playground.service.document.request.CreateDocumentRequest;
import playground.service.document.response.SelectCategoryResponse;
import playground.service.document.response.SelectDocumentResponse;
import playground.service.document.response.SelectSingleOutBoxResponse;
import playground.service.user.UserService;

import javax.persistence.EntityNotFoundException;
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
    public void create(final CreateDocumentRequest createDocumentRequest) {
        List<Long> approverIds = createDocumentRequest.getApproverIds();
        checkApprovalExistence(approverIds);

        Long drafterId = createDocumentRequest.getDrafterId();
        User drafter = userService.findById(drafterId);
        Document document = createDocumentRequest.toDocument(drafter);
        List<User> approvers = findAllUserById(approverIds);
        document.enrollApprovals(approvers, document);
        documentRepository.save(document);
    }

    private void checkApprovalExistence(final List<Long> approvalIds) {
        List<User> users = userService.findAllById(approvalIds);

        if (approvalIds.size() != users.size()) {
            throw new EntityNotFoundException(String.format("%s 식별번호와 일치하는 결재자를 모두 찾지 못했습니다.", approvalIds));
        }
    }

    private List<User> findAllUserById(final List<Long> approverIds) {
        return approverIds.stream()
                .map(userService::findById)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SelectDocumentResponse find(final Long documentId) {
        Document document = findDocumentAndDrafterById(documentId);

        List<Long> documentApprovalIds = document.getDocumentApprovals()
                .stream()
                .map(DocumentApproval::getId)
                .collect(Collectors.toList());
        List<DocumentApproval> documentApprovals = documentApprovalRepository.findAllDocumentApprovalAndApproverAndTeamByIds(documentApprovalIds);

        return new SelectDocumentResponse(document, documentApprovals);
    }

    private Document findDocumentAndDrafterById(final Long documentId) {
        return documentRepository.findDocumentAndDrafterById(documentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("[%d] 식별번호에 해당하는 문서가 존재하지 않습니다.", documentId)));
    }

    @Transactional(readOnly = true)
    public List<SelectSingleOutBoxResponse> findOutBox(final Long drafterId) {
        List<Document> documents = documentRepository.findAllDocumentAndDrafterByDrafterIdAndApprovalState(drafterId, ApprovalState.DRAFTING);
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

    public List<SelectCategoryResponse> findCategories() {
        return documentRepository.findCategories();
    }
}
