package playground.learning;

import lombok.Builder;

public class DocumentApproval {

    private final User approver;
    private final int approvalOrder;

    private ApprovalState approvalState;
    private String approvalComment;

    @Builder
    private DocumentApproval(User approver, int approvalOrder) {
        this.approver = approver;
        this.approvalOrder = approvalOrder;
        this.approvalState = ApprovalState.DRAFTING;
        this.approvalComment = null;
    }

    public static DocumentApproval of(User approver, int approvalOrder) {
        return DocumentApproval.builder()
                .approver(approver)
                .approvalOrder(approvalOrder)
                .build();
    }

    public boolean isNotFor(User approver) {
        return !isFor(approver);
    }

    public boolean isFor(User approver) {
        return this.approver.equals(approver);
    }

    public void approve() {
        approve(null);
    }

    public void approve(String approvalComment) {
        approvalState = approvalState.approve();
        this.approvalComment = approvalComment;
    }

    public boolean isDrafting() {
        return approvalState.isDrafting();
    }

    public boolean isApproved() {
        return approvalState.isApproved();
    }
}
