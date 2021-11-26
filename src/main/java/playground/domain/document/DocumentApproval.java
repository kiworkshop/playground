package playground.domain.document;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;
import playground.type.ApprovalState;

import javax.persistence.*;

@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Table(name = "documentApproval")
@Entity
public class DocumentApproval {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User approver;

    @ManyToOne(fetch = FetchType.LAZY)
    private Document document;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

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