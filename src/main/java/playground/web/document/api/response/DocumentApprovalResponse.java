package playground.web.document.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.DocumentApproval;
import playground.domain.user.Team;
import playground.domain.user.User;

@Getter
@NoArgsConstructor
public class DocumentApprovalResponse {

    private String approverTeamName;
    private String approverName;
    private ApprovalState approvalState;
    private Integer approvalOrder;
    private String approvalComment;

    public DocumentApprovalResponse(DocumentApproval documentApproval, User approver, Team team) {
        this.approverTeamName = team.getName();

        this.approverName = approver.getName();

        this.approvalState = documentApproval.getApprovalState();
        this.approvalOrder = documentApproval.getApprovalOrder();
        this.approvalComment = documentApproval.getApprovalComment();
    }

    public String getApprovalState() {
        return approvalState.name();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
