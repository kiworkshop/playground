package playground.dto;

import learning.ApprovalState;
import learning.Category;
import learning.Document;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutboxResponse {
    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;
    private String categoryText;
    private String approvalStateText;

    public static OutboxResponse convertFrom(Document document) {
        return OutboxResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .category(document.getCategory())
                .approvalState(document.getApprovalState())
                .categoryText(document.getCategory().getText())
                .approvalStateText(document.getApprovalState().getText())
                .build();
    }
}
