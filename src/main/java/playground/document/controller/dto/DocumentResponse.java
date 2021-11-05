package playground.document.controller.dto;

import lombok.Builder;
import playground.document.entity.Document;
import playground.document.type.ApprovalState;
import playground.document.type.Category;
import playground.user.entity.User;

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

    public static DocumentResponse from(Document document, User user) {
        return DocumentResponse.builder()
            .id(document.getId())
            .title(document.getTitle())
            .category(document.getCategory())
            .contents(document.getContents())
            .userId(user.getId())
            .approvalState(document.getApprovalState())
            .userName(user.getName())
            .categoryText(document.getCategory().getText())
            .approvalStateText(document.getApprovalState().getText())
            .build();
    }
}
