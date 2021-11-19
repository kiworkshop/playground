package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import playground.type.ApprovalState;
import playground.type.Category;

@Getter
@Entity
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