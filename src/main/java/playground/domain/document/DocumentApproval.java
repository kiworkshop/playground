package playground.domain.document;

import lombok.*;
import playground.domain.user.User;
import playground.common.type.ApprovalState;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document_approval")
@Entity
public class DocumentApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Document document;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalState approvalState;

    @Column(nullable = false)
    private Integer approvalOrder;

    private String approvalComment;

    @Builder
    public DocumentApproval(User approver, ApprovalState approvalState, Integer approvalOrder, String approvalComment) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}