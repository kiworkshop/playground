package playground.service.document.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.DocumentApproval;
import playground.domain.document.vo.ApprovalState;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectApproverResponse {

    private String approverTeamName;
    private String approverName;
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;
    private String approvalStateText;

    public SelectApproverResponse(final DocumentApproval documentApproval) {
        this.approverTeamName = documentApproval.getApprover().getTeam().getName();
        this.approverName = documentApproval.getApprover().getName();
        ;
        this.approvalState = documentApproval.getApprovalState();
        this.approvalOrder = documentApproval.getApprovalOrder();
        this.approvalComment = documentApproval.getApprovalComment();
        this.approvalStateText = documentApproval.getApprovalState().getText();
    }
}
