package playground.dto;

import lombok.Getter;
import lombok.Setter;
import playground.domain.Document;

@Getter
@Setter
public class DocumentOutboxResponse {
    private Long id;
    private String title;
    private String category;
    private String approvalState;
    private String categoryText;
    private String approvalStateText;

    public DocumentOutboxResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.approvalState = document.getApprovalState().name();
        this.categoryText = document.getCategory().getCategory();
        this.approvalStateText = document.getApprovalState().getStatus();
    }
}
