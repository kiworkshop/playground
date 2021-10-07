package learning;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentApproval {

    private User approver;
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;

    @Builder
    public DocumentApproval(final User approver, final ApprovalState approvalState,
                            final Integer approvalOrder, final String approvalComment) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public boolean isApproved() {
        return approvalState.isApproved();
    }

    public boolean isNotApproved() {
        return !isApproved();
    }

    public boolean hasNotSameApprover(final User approver) {
        return !this.approver.isSame(approver);
    }

    public void changeStateToApproved(final String comment) {
        approvalState = ApprovalState.APPROVED;
        approvalComment = comment;
    }

    public boolean isLastApproval(final int lastApprovalOrder) {
        return this.approvalOrder == lastApprovalOrder;
    }
}
