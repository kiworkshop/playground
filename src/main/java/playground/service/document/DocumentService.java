package playground.service.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import playground.dao.document.DocumentApprovalDao;
import playground.dao.document.DocumentDao;
import playground.dao.document.dto.DocumentTitleResponse;
import playground.dao.user.UserDao;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.user.User;
import playground.service.document.dto.DocumentResponseDto;
import playground.web.document.dto.DocumentCreateRequest;
import playground.web.document.dto.DocumentOutboxRequest;

import java.util.List;
import java.util.stream.Collectors;

import static playground.domain.document.ApprovalState.DRAFTING;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DocumentService {

    private final DocumentDao documentDao;
    private final DocumentApprovalDao documentApprovalDao;
    private final UserDao userDao;

    public List<DocumentTitleResponse> findOutboxDocuments(DocumentOutboxRequest request) {
        List<Document> outboxDocuments = documentDao.findStateDocumentsByDrafterId(request.getDrafterId(), DRAFTING);
        return convertTitleDtoFrom(outboxDocuments);
    }

    public DocumentResponseDto findDocument(Long documentId) {
        Document document = documentDao.findById(documentId);
        User drafter = userDao.findById(document.getDrafterId());

        return new DocumentResponseDto(document, drafter);
    }

    @Transactional
    public void create(DocumentCreateRequest request) {
        Document document = request.toEntity();
        Long documentId = documentDao.save(document);

        List<Long> approverIds = request.getApproverIds();

        for (int index = 0; index < approverIds.size(); index++) {
            DocumentApproval documentApproval = DocumentApproval.builder()
                    .documentId(documentId)
                    .approvalState(DRAFTING)
                    .approverId(approverIds.get(index))
                    .approvalOrder(index + 1)
                    .build();
            documentApprovalDao.save(documentApproval);
        }
    }

    private List<DocumentTitleResponse> convertTitleDtoFrom(List<Document> documents) {
        return documents.stream()
                .map(DocumentTitleResponse::new)
                .collect(Collectors.toList());
    }

}
