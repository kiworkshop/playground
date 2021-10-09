package learning;

import lombok.Builder;
import lombok.Getter;

import static learning.ApprovalState.APPROVED;
import static learning.ApprovalState.DRAFTING;

@Getter
public class DocumentApproval {

    private User approver;
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;

    @Builder
    private DocumentApproval(User approver, ApprovalState approvalState, Integer approvalOrder, String approvalComment) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public static DocumentApproval of(User approver, Integer approvalOrder) {
        return DocumentApproval.builder()
                .approver(approver)
                .approvalOrder(approvalOrder)
                .approvalComment(null)
                .approvalState(DRAFTING)
                .build();
    }

    public void approve(String approvalComment) {
        this.approvalState = APPROVED;
        this.approvalComment = approvalComment;
    }

    public boolean isSameApprover(User approver) {
        return this.approver.equals(approver);
    }

    public boolean isProgressing() {
        return this.approvalState.isDrafting();
    }

    public boolean isApproved() {
        return this.approvalState.isApproved();
    }

    public boolean isCanceled() {
        return this.approvalState.isCanceled();
    }

}
