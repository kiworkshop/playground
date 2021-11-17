package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.User;

import static playground.domain.document.ApprovalState.*;


@Getter
public class DocumentApproval {

    private final Long documentId;
    private final User approver;
    private ApprovalState approvalState;
    private final int approvalOrder;
    private String approvalComment;

    @Builder
    public DocumentApproval(Long documentId, User approver, int approvalOrder) {
        this.documentId = documentId;
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
