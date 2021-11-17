package learning;

import lombok.Builder;
import lombok.Getter;

import static learning.ApprovalState.*;

@Getter
public class DocumentApproval {

    private final User approver;
    private ApprovalState approvalState;
    private final int approvalOrder;
    private String approvalComment;

    @Builder
    public DocumentApproval(User approver, int approvalOrder) {
        this.approver = approver;
        this.approvalState = DRAFTING;
        this.approvalOrder = approvalOrder;
    }

    public void approve(String approvalComment) {
        this.approvalState = APPROVED;
        this.approvalComment = approvalComment;
    }

    public void reject(String approvalComment) {
        this.approvalState = CANCELED;
        this.approvalComment = approvalComment;
    }

    public boolean isApproved() {
        return approvalState.isApproved();
    }

    public Long getApproverId() {
        return approver.getId();
    }

}
