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

    public void approve(String approvalComment) {
        this.approvalState = APPROVED;
        this.approvalComment = approvalComment;
    }

    public boolean isApproved() {
        return approvalState.isApproved();
    }

    public Long getApproverId() {
        return approver.getId();
    }

}
