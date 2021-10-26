package learning.document;

import learning.user.User;
import lombok.Builder;
import lombok.Getter;

import static learning.document.ApprovalState.DRAFTING;

@Getter
public class DocumentApproval {

    private User approver;
    private ApprovalState approvalState;
    private int approvalOrder;
    private String approvalComment;
    @Builder
    public DocumentApproval(User approver, ApprovalState approvalState, int approvalOrder, String approvalComment) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public static DocumentApproval of(User approver, int approvalOrder) {
        return new DocumentApproval(approver, DRAFTING, approvalOrder, null);
    }

    public void update(ApprovalState approvalState, String approvalComment) {
        this.approvalState = approvalState;
        this.approvalComment= approvalComment;
    }

    public boolean isOwnedBy(User user) {
        return this.approver.equals(user);
    }
}
