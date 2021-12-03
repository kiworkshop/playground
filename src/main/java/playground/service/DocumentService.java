package playground.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import playground.domain.Category;
import playground.domain.Document;
import playground.domain.DocumentApproval;
import playground.domain.User;
import playground.dto.*;
import playground.repository.DocumentApprovalRepository;
import playground.repository.DocumentRepository;
import playground.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentService {
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DocumentApprovalRepository documentApprovalRepository;

    @Transactional
    public DocumentResponse findDocumentBy(Long documentId) {
        Document document = documentRepository.findById(documentId).get();
        return new DocumentResponse(document);
    }

    @Transactional
    public List<DocumentOutboxResponse> findDocumentsOutbox(Long userId) {
        List<Document> documents = documentRepository.findByDrafterIdOrderByCreatedDateDesc(userId);
        List<DocumentOutboxResponse> documentOutboxDtos = new ArrayList<>();
        for (Document document : documents) {
            documentOutboxDtos.add(new DocumentOutboxResponse(document));
        }
        return documentOutboxDtos;
    }

    @Transactional
    public long insertDocument(DocumentRequest documentRequest) {
        Document document = getDocumentBy(documentRequest);
        documentRepository.save(document);
        List<DocumentApproval> documentApprovals = getDocumentApprovals(documentRequest.getApproverIds(), document);
        documentApprovals.stream().forEach((documentApproval) -> documentApprovalRepository.save(documentApproval));
        return document.getId();
    }

    @Transactional
    private Document getDocumentBy(DocumentRequest documentRequest) {
        User drafter = userRepository.findById(documentRequest.getDrafterId()).get();
        return Document.builder()
                .title(documentRequest.getTitle())
                .category(Category.valueOf(documentRequest.getCategory()))
                .contents(documentRequest.getContents())
                .drafter(drafter)
                .build();
    }

    @Transactional
    public List<CategoryResponse> getCategories() {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : Category.values()) {
            categoryResponses.add(new CategoryResponse(category.toString(), category.getName()));
        }
        return categoryResponses;
    }

    private List<DocumentApproval> getDocumentApprovals(List<Long> approvars, Document document) {
        List<DocumentApproval> documentApprovals = new ArrayList<>();
        for (int i = 0; i < approvars.size(); i++) {
            User approvar = userRepository.findById(approvars.get(i)).get();
            documentApprovals.add(new DocumentApproval(approvar, i, document));
        }
        return documentApprovals;
    }
}
