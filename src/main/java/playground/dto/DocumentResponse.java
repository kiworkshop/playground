package playground.dto;

import learning.ApprovalState;
import learning.Category;
import learning.Document;
import lombok.Builder;
import lombok.Getter;

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
    private String categoryText;
    private String approvalStateText;

    public static DocumentResponse convertFrom(Document document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .category(document.getCategory())
                .contents(document.getContents())
                .userId(document.getDrafter().getId())
                .userName(document.getDrafter().getName())
                .approvalState(document.getApprovalState())
                .categoryText(document.getCategory().getText())
                .approvalStateText(document.getApprovalState().getText())
                .build();
    }
}
