package learning.domain;

import lombok.Builder;
import lombok.Setter;
import learning.constants.ApprovalState;

@Builder
@Setter
public class DocumentApproval {

    private final User approver;
    private String approvalComment;
    private ApprovalState approvalState;
    private Integer approvalOrder;

    public boolean identifyApprover(User approver) {
        return this.approver.equals(approver);
    }

    public boolean isApproved() {
        return approvalState == ApprovalState.APPROVED;
    }

    public boolean isNotApproved() {
        return !isApproved();
    }
}
