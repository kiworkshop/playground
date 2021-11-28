package playground.service.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.domain.user.User;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;

@Getter
@NoArgsConstructor
public class DocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private Long userId;
    private ApprovalState approvalState;
    private String userName;

    public DocumentResponse(Document document, User drafter) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.contents = document.getContents();
        this.approvalState = document.getApprovalState();

        this.userId = drafter.getId();
        this.userName = drafter.getName();
    }

    public String getCategoryText() {
        return category.getText();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
