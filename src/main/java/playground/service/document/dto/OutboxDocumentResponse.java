package playground.service.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.common.type.ApprovalState;
import playground.common.type.Category;

@Getter
@NoArgsConstructor
public class OutboxDocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;

    public OutboxDocumentResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.approvalState = document.getApprovalState();
    }

    public String getCategoryText() {
        return category.getText();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }
}
