package playground.domain.document.approval;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.BaseTimeEntity;
import playground.domain.document.Document;
import playground.domain.user.User;

import javax.persistence.*;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static playground.domain.document.approval.ApprovalState.DRAFTING;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class DocumentApproval extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "document_id", foreignKey = @ForeignKey(value = NO_CONSTRAINT))
    private Document document;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "approver_id", foreignKey = @ForeignKey(value = NO_CONSTRAINT))
    private User approver;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

    private int approvalOrder;

    @Lob
    private String approvalComment;

    @Builder
    public DocumentApproval(User approver, int approvalOrder) {
        this.approver = approver;
        this.approvalState = DRAFTING;
        this.approvalOrder = approvalOrder;
    }

    public void upDateDocument(Document document) {
        this.document = document;
    }
}
