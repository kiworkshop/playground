package playground.dto;

import lombok.Getter;
import lombok.Setter;
import playground.domain.Document;

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

    public DocumentResponse(Document document, UserResponse drafter, List<ApprovalResponse> approvers) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.contents = document.getContents();
        this.drafter = drafter;
        this.approvers = approvers;
        this.categoryText = document.getCategory().getName();
        this.approvalStateText = document.getApprovalState().getName();
    }

//    public DocumentResponse(Document document, String userName) {
//        this.id = document.getId();
//        this.title = document.getTitle();
//        this.category = document.getCategory().name();
//        this.contents = document.getContents();
//        this.userId = document.getDrafter().getId();
//        this.approvalState = document.getApprovalState().name();
//        this.userName = document.getDrafter().getName();
//        this.categoryText = document.getCategory().getName();
//        this.approvalStateText = document.getApprovalState().name();
//        this.userName = userName;
//    }
//
//    public DocumentResponse(Document document) {
//        this.id = document.getId();
//        this.title = document.getTitle();
//        this.category = document.getCategory().name();
//        this.contents = document.getContents();
//        this.userId = document.getDrafter().getId();
//        this.approvalState = document.getApprovalState().name();
//        this.userName = document.getDrafter().getName();
//        this.categoryText = document.getCategory().getName();
//        this.approvalStateText = document.getApprovalState().getName();
//        this.userName = document.getDrafter().getName();
//    }
}
