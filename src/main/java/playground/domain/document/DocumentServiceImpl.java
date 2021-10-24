package playground.domain.document;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;
import playground.domain.document.dao.DocumentApprovalDao;
import playground.domain.document.dao.DocumentDao;
import playground.domain.document.dto.AddDocumentRequest;
import playground.domain.document.dto.OutBox;
import playground.domain.document.dto.SingleDocument;
import playground.domain.document.dto.param.AddDocumentApprovalParam;
import playground.domain.document.dto.param.AddDocumentParam;
import playground.domain.document.entity.Document;
import playground.domain.document.entity.Documents;
import playground.domain.user.dao.UserDao;
import playground.domain.user.entity.User;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentDao documentDao;
    private final DocumentApprovalDao documentApprovalDao;
    private final UserDao userDao;

    @Override
    public SingleDocument findById(Long id) {
        try {
            Document document = documentDao.findById(id);
            User drafter = userDao.findDrafterOf(document);

            return SingleDocument.of(document, drafter);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new NoSuchElementException("id에 맞는 document가 없음");
        }
    }

    @Override
    public Long addDocument(AddDocumentRequest addDocumentRequest) {
        AddDocumentParam addDocumentParam = AddDocumentParam.of(addDocumentRequest);
        Long documentId = documentDao.addDocument(addDocumentParam);

        AddDocumentApprovalParam addDocumentApprovalParam = AddDocumentApprovalParam.of(documentId, addDocumentRequest);
        addDocumentApproval(addDocumentApprovalParam);

        return documentId;
    }

    @Override
    public OutBox findOutboxOf(Long drafterId) {
        List<Document> elements = documentDao.findByDrafter(drafterId);
        Documents documents = new Documents(elements);

        return OutBox.of(documents);
    }

    private void addDocumentApproval(AddDocumentApprovalParam addDocumentApprovalParam) {
        try {
            documentApprovalDao.addApprovals(addDocumentApprovalParam);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("존재하지 않는 유저를 결재자로 등록함");
        }
    }
}
