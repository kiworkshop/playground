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

    public DocumentResponse findDocumentBy(Long documentId) {
        Document document = documentRepository.findById(documentId).get();
        //Drafter
        //approvers
        //categoryText
        //approvalStatText
        User drafter = userRepository.findById(document.getDrafter().getId()).get();
        List<DocumentApproval> approvars = documentApprovalRepository.findByDocument(document);
        List<ApprovalResponse> approvalsResponse = new ArrayList<>();
        for (DocumentApproval approvar : approvars) {
            approvalsResponse.add(new ApprovalResponse(approvar));
        }
        return new DocumentResponse(document, new UserResponse(drafter), approvalsResponse);
    }

    public List<DocumentOutboxResponse> findDocumentsOutbox(Long userId) {
        List<Document> documents = documentRepository.findByDrafterId(userId);
        List<DocumentOutboxResponse> documentOutboxDtos = new ArrayList<>();
        for (Document document : documents) {
            documentOutboxDtos.add(new DocumentOutboxResponse(document));
        }
        return documentOutboxDtos;
    }

    public long insertDocument(DocumentRequest documentRequest) {
        Document document = getDocumentBy(documentRequest);
        documentRepository.save(document);
        return document.getId();
    }

    private Document getDocumentBy(DocumentRequest documentRequest) {
        User drafter = userRepository.findById(documentRequest.getDrafterId()).get();
        return Document.builder()
                .title(documentRequest.getTitle())
                .category(Category.valueOf(documentRequest.getCategory()))
                .contents(documentRequest.getContents())
                .drafter(drafter)
                .build();
    }

    public List<CategoryResponse> getCatcories() {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Category category : Category.values()) {
            categoryResponses.add(new CategoryResponse(category.toString(), category.getName()));
        }
        return categoryResponses;
    }
}
