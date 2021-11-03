package playground.domain.document;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DocumentApproval {

    private Long approverId;
    private Long documentId;
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;

    @Builder
    private DocumentApproval(final Long approverId, final Long documentId, final String approvalState,
                             final Integer approvalOrder, final String approvalComment) {
        this.approverId = approverId;
        this.documentId = documentId;
        this.approvalState = ApprovalState.valueOf(approvalState);
        this.approvalOrder = approvalOrder;
        this.approvalComment = approvalComment;
    }

    public static DocumentApproval of(final Long approverId, final Long documentId, final int approvalOrder) {
        return new DocumentApproval(approverId, documentId, ApprovalState.DRAFTING.name(),
                approvalOrder, null);
    }
}

