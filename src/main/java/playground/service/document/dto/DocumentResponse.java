package playground.service.document.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.document.approval.ApprovalState;

@Getter
@Builder
public class DocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private Long userId;
    private ApprovalState approvalState;
    private String userName;

    public static DocumentResponse convertFrom(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .category(document.getCategory())
                .contents(document.getContents())
                .userId(document.getDrafter().getId())
                .userName(document.getDrafter().getName())
                .approvalState(document.getApprovalState())
                .build();
    }

    public String getCategoryText() {
        return category.getText();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }
}
