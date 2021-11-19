package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENT_APPROVAL")
@Getter
@NoArgsConstructor
public class DocumentApproval {

    @Id
    @Column(name = "ID")
    private Long id;

    private Long documentId;
    private Long approverId;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;

    @Builder
    public DocumentApproval(Long id, Long documentId, Long approverId, ApprovalState approvalState, Integer approvalOrder, String approvalComment) {
        this.id = id;
        this.documentId = documentId;
        this.approverId = approverId;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

}
