package playground.service.document.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.domain.user.User;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectDocumentResponse {

    private Long id;
    private String title;
    private String category;
    private String contents;
    private Long userId;
    private String approvalState;
    private String userName;
    private String categoryText;
    private String approvalStateText;

    public SelectDocumentResponse(final Document document, final User user) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.contents = document.getContents();
        this.userId = user.getId();
        this.approvalState = document.getApprovalState().name();
        this.userName = user.getName();
        this.categoryText = document.getCategory().getText();
        this.approvalStateText = document.getApprovalState().getText();
    }
}
