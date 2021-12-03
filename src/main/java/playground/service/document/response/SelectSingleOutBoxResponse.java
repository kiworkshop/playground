package playground.service.document.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectSingleOutBoxResponse {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;
    private String categoryText;
    private String approvalStateText;

    public SelectSingleOutBoxResponse(final Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.approvalState = document.getApprovalState();
        this.categoryText = document.getCategory().getText();
        this.approvalStateText = document.getApprovalState().getText();
    }
}
