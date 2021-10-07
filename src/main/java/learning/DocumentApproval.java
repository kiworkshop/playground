package learning;

import lombok.Builder;

@Builder
public class DocumentApproval {
    private final User approver;
    private final ApprovalState approvalState;
    private final int approvalOrder;
    private final String approvalComment;
}
