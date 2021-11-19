package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import playground.type.ApprovalState;

@Getter
public class DocumentApproval {

    private Long documentApprovalId;
    private Long approverId;
    private Long documentId;
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;

    @Builder
    public DocumentApproval(Long documentApprovalId, Long approverId, Long documentId, ApprovalState approvalState, Integer approvalOrder, String approvalComment) {
        this.documentApprovalId = documentApprovalId;
        this.approverId = approverId;
        this.documentId = documentId;
        this.approvalState = approvalState;
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }
}