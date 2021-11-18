package playground.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class DocumentApproval {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User approver;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

    private int approvalOrder;

    private String approvalComment;

    @Builder
    public DocumentApproval(User approver, ApprovalState approvalState, int approvalOrder) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
    }

    public DocumentApproval approveBy(User approver, String approvalComment) {
        this.approvalComment = approvalComment;
        this.approver = approver;
        this.approvalState = ApprovalState.APPROVED;
        return this;
    }
}
