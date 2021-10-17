package playground.dto;

import learning.ApprovalState;
import learning.Document;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private Long id;
    private String title;
    private String category;
    private String contents;
    private Long userId;
    private String userName;
    private ApprovalState approvalState;
    private String categoryText;
    private String approvalStateText;
    private Long drafterId;
    private List<Long> approverIds;

    public static DocumentDto convertFrom(Document document) {
        return DocumentDto.builder()
                .id(document.getId())
                .title(document.getTitle())
                .category(document.getCategory().getText())
                .contents(document.getContents())
                .userId(document.getDrafter().getId())
                .userName(document.getDrafter().getName())
                .approvalState(document.getApprovalState())
                .categoryText(document.getCategory().getText())
                .approvalStateText(document.getApprovalState().getText())
                .build();
    }

    public static DocumentDto convertOutBoxFrom(Document document) {
        return DocumentDto.builder()
                .id(document.getId())
                .title(document.getTitle())
                .category(document.getCategory().getText())
                .approvalState(document.getApprovalState())
                .categoryText(document.getCategory().getText())
                .approvalStateText(document.getApprovalState().getText())
                .build();
    }
}