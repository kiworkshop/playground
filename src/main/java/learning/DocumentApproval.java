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
}
