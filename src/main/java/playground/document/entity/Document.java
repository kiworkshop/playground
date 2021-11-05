package playground.document.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import playground.document.type.ApprovalState;
import playground.document.type.Category;

@Getter
public class Document {

    @Setter
    private Long id;
    private String title;
    private Category category;
    private String contents;
    private Long drafterId;
    private ApprovalState approvalState;

    @Builder
    public Document(String title, Category category, String contents, Long drafterId, ApprovalState approvalState) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafterId = drafterId;
        this.approvalState = approvalState;
    }
}