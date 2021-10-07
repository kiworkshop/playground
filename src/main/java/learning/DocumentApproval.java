package learning;

import lombok.Getter;

@Getter
public class DocumentApproval {
    private final User approver;
    private final ApprovalState approvalState;
    private final int approvalOrder;
    private String approvalComment;

    public DocumentApproval(User approver, int approvalOrder) {
        this.approver = approver;
        this.approvalState = ApprovalState.DRAFTING;
        this.approvalOrder = approvalOrder;
    }
}
