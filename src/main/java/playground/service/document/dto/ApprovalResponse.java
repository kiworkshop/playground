package playground.service.document.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.approval.ApprovalState;
import playground.domain.document.approval.DocumentApproval;
import playground.domain.user.User;

@Getter
@Builder
public class ApprovalResponse {

    private String approverTeamName;
    private String approverName;
    private ApprovalState approvalState;
    private int approvalOrder;
    private String approvalComment;
    private String approvalStateText;

    public static ApprovalResponse of(DocumentApproval approval, User approver) {
        return ApprovalResponse.builder()
                .approverTeamName(approver.getTeamName())
                .approverName(approver.getName())
                .approvalState(approval.getApprovalState())
                .approvalOrder(approval.getApprovalOrder())
                .approvalComment(approval.getApprovalComment())
                .build();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }
}
