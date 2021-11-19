package playground.service.document.dto;

import lombok.Builder;
import playground.domain.document.Document;
import playground.type.ApprovalState;
import playground.type.Category;
import playground.domain.user.User;

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
