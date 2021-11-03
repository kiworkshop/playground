package playground.service.document.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectSingleOutBoxResponse {

    private Long id;
    private String title;
    private String category;
    private String approvalState;
    private String categoryText;
    private String approvalStateText;

    public SelectSingleOutBoxResponse(final Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory().name();
        this.approvalState = document.getApprovalState().name();
        this.categoryText = document.getCategory().getText();
        this.approvalStateText = document.getApprovalState().getText();
    }
}
