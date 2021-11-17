package playground.service.document.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;

@Getter
@Builder
public class OutboxResponse {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;

    public static OutboxResponse convertFrom(Document document) {
        return OutboxResponse.builder()
                .id(document.getId())
                .title(document.getTitle())
                .category(document.getCategory())
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
