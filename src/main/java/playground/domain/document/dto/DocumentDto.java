package playground.domain.document.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import playground.domain.document.entity.Document;
import playground.domain.user.entity.User;

@Getter
@Builder
@RequiredArgsConstructor
public class DocumentDto {

    private final Long id;
    private final String title;
    private final String contents;
    private final Long userId;
    private final String userName;
    private final String category;
    private final String categoryText;
    private final String approvalState;
    private final String approvalStateText;

    public static DocumentDto of(Document document, User drafter) {
        return DocumentDto.builder()
                .id(document.getId())
                .title(document.getTitle())
                .contents(document.getContents())
                .userId(drafter.getId())
                .userName(drafter.getName())
                .category(document.getCategory().name())
                .categoryText(document.getCategory().getText())
                .approvalState(document.getApprovalState().name())
                .approvalStateText(document.getApprovalState().getText())
                .build();
    }
}
