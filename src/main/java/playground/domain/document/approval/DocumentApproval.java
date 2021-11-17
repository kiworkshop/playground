package playground.domain.document.approval;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.BaseTimeEntity;
import playground.domain.document.Document;
import playground.domain.user.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static playground.domain.document.approval.ApprovalState.DRAFTING;

@Getter
@Entity
@NoArgsConstructor
public class DocumentApproval extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Document document;

    @OneToOne
    private User approver;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

    private int approvalOrder;

    @Lob
    private String approvalComment;

    private DocumentApproval(User approver, int approvalOrder) {
        this.approver = approver;
        this.approvalState = DRAFTING;
        this.approvalOrder = approvalOrder;
    }

    public static DocumentApproval create(User approver, int approvalOrder) {
        return new DocumentApproval(approver, approvalOrder);
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
