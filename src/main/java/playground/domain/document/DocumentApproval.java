package playground.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class DocumentApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Document document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User approver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalState approvalState;

    @Column(nullable = false)
    private Integer approvalOrder;

    private String approvalComment;

    @Builder
    private DocumentApproval(User approver, ApprovalState approvalState, Integer approvalOrder, String approvalComment) {
        this.approver = approver;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public void updateDocument(Document document) {
        this.document = document;
    }

}
