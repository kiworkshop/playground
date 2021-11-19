package playground.service.document.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectDocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private Long userId;
    private ApprovalState approvalState;
    private String userName;
    private String categoryText;
    private String approvalStateText;

    public SelectDocumentResponse(final Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.contents = document.getContents();
        this.userId = document.getDrafter().getId();
        this.approvalState = document.getApprovalState();
        this.userName = document.getDrafter().getName();
        this.categoryText = document.getCategory().getText();
        this.approvalStateText = document.getApprovalState().getText();
    }
}
