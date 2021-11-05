package playground.document.controller.dto;

import lombok.Builder;
import lombok.Getter;
import playground.document.entity.Document;
import playground.document.type.ApprovalState;
import playground.document.type.Category;

@Builder
@Getter
public class OutboxDocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;
    private String categoryText;
    private String approvalStateText;

    public static OutboxDocumentResponse from(Document document) {
        return OutboxDocumentResponse.builder()
            .id(document.getId())
            .title(document.getTitle())
            .category(document.getCategory())
            .approvalState(document.getApprovalState())
            .categoryText(document.getCategory().getText())
            .approvalStateText(document.getApprovalState().getText())
            .build();
    }
}
