package playground.document.entity;

import lombok.Builder;
import lombok.Getter;
import playground.user.entity.User;

@Builder
@Getter
public class Document {

    private Long id;
    private String title;
    private String contents;
    private User drafter;
    private Category category;
    private ApprovalState approvalState;
}
