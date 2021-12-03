package playground.dto;

import lombok.Getter;
import lombok.Setter;
import playground.domain.Document;
import playground.domain.DocumentApproval;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DocumentResponse {
    private Long id;
    private String title;
    private String category;
    private String contents;
    private UserResponse drafter;
    private List<ApprovalResponse> approvers;
    private String categoryText;
    private String approvalStateText;

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.contents = document.getContents();
        this.drafter = new UserResponse(document.getDrafter());
        this.approvers = getApprovars(document.getDocumentApprovals());
        this.categoryText = document.getCategory().getName();
        this.approvalStateText = document.getApprovalState().getName();
    }

    private List<ApprovalResponse> getApprovars(List<DocumentApproval> approvars) {
        List<ApprovalResponse> approvarsResponse = new ArrayList<>();
        for (DocumentApproval documentApproval : approvars) {
            approvarsResponse.add(new ApprovalResponse(documentApproval));
        }
        return approvarsResponse;
    }
}
