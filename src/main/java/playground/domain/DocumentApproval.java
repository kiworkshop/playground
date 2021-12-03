package playground.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class DocumentApproval {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User approver;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

    private int approvalOrder;

    private String approvalComment;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Builder
    public DocumentApproval(User approver, ApprovalState approvalState, int approvalOrder) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
    }
    @Builder
    public DocumentApproval(User approver, int approvalOrder, Document document) {
        this.approver = approver;
        this.approvalState = ApprovalState.DRAFTING;
        this.approvalOrder = approvalOrder;
        this.document = document;
    }
    public DocumentApproval approveBy(User approver, String approvalComment) {
        this.approvalComment = approvalComment;
        this.approver = approver;
        this.approvalState = ApprovalState.APPROVED;
        return this;
    }
}
