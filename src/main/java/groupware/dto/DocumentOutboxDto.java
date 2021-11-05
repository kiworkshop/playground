package groupware.dto;

import groupware.domain.Document;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentOutboxDto {
    private Long id;
    private String title;
    private String category;
    private String approvalState;
    private String categoryText;
    private String approvalStateText;

    public DocumentOutboxDto(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.approvalState = document.getApprovalState().name();
        this.categoryText = document.getCategory().getCategory();
        this.approvalStateText = document.getApprovalState().getStatus();
    }
}
