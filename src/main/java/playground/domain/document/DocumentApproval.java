package playground.domain.document;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DocumentApproval {

    private Long id;

    private Long documentId;
    private Long approverId;

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
