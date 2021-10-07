package learning;

import lombok.Getter;

import static learning.ApprovalState.APPROVED;
import static learning.ApprovalState.DRAFTING;

@Getter
public class DocumentApproval {
    private final User approver;
    private ApprovalState approvalState;
    private final int approvalOrder;
    private String approvalComment;

    public DocumentApproval(User approver, int approvalOrder) {
        this.approver = approver;
        this.approvalState = DRAFTING;
        this.approvalOrder = approvalOrder;
    }

    public long getApproverId() {
        return approver.getId();
    }

    public void approve(String approvalComment) {
        this.approvalState = APPROVED;
        this.approvalComment = approvalComment;
    }
}
