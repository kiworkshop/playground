package playground.dto;

import playground.domain.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentResponse {
    private Long id;
    private String title;
    private String category;
    private String contents;
    private long userId;
    private String approvalState;
    private String userName;
    private String categoryText;
    private String approvalStateText;

    public DocumentResponse(Document document, String userName) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.contents = document.getContents();
        this.userId = document.getDrafter().getId();
        this.approvalState = document.getApprovalState().name();
        this.userName = document.getDrafter().getName();
        this.categoryText = document.getCategory().getCategory();
        this.approvalStateText = document.getApprovalState().getStatus();
        this.userName = userName;
    }

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.contents = document.getContents();
        this.userId = document.getDrafter().getId();
        this.approvalState = document.getApprovalState().name();
        this.userName = document.getDrafter().getName();
        this.categoryText = document.getCategory().getCategory();
        this.approvalStateText = document.getApprovalState().getStatus();
        this.userName = document.getDrafter().getName();
    }
}
