package playground.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.vo.ApprovalState;
import playground.domain.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document_approval")
public class DocumentApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_approval_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private User approver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "approval_state", nullable = false)
    private ApprovalState approvalState;

    @Column(name = "approval_order", nullable = false)
    private Integer approvalOrder;

    @Column(name = "approval_comment")
    private String approvalComment;

    @Builder
    private DocumentApproval(final User approver, final Document document, final ApprovalState approvalState,
                             final Integer approvalOrder, final String approvalComment) {
        this.approver = approver;
        this.document = document;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public static DocumentApproval of(final User approver, final Document document, final int approvalOrder) {
        return new DocumentApproval(approver, document, ApprovalState.DRAFTING,
                approvalOrder, null);
    }
}

