package learning.document;

import learning.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DocumentApproval {

    private User approver;
    private ApprovalState approvalState;
    private int approvalOrder;
    private String approvalComment;

    public void update(ApprovalState approvalState, String approvalComment) {
        this.approvalState = approvalState;
        this.approvalComment= approvalComment;
    }

    public boolean isOwnedBy(User user) {
        return this.approver.equals(user);
    }
}
