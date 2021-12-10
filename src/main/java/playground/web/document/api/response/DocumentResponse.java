package playground.web.document.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.web.user.api.response.UserResponse;

import java.util.List;

@Getter
@NoArgsConstructor
public class DocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private ApprovalState approvalState;
    private UserResponse drafter;
    private List<DocumentApprovalResponse> approvers;

    public DocumentResponse(Document document, UserResponse drafter, List<DocumentApprovalResponse> approvers) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.contents = document.getContents();
        this.approvalState = document.getApprovalState();

        this.drafter = drafter;
        this.approvers = approvers;
    }

    public String getCategoryText() {
        return category.getText();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
