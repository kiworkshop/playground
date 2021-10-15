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

    @Builder(builderClassName = "DocumentApprovalBuilder", builderMethodName = "create")
    public DocumentApproval(User approver, int approvalOrder) {
        this.approver = approver;
        this.approvalState = DRAFTING;
        this.approvalOrder = approvalOrder;
    }

    @Builder(builderClassName = "DocumentAllBuilder", builderMethodName = "rowMapper")
    public DocumentApproval(User approver, ApprovalState approvalState, int approvalOrder, String approvalComment) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
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
