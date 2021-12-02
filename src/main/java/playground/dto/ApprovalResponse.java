package playground.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import playground.domain.DocumentApproval;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalResponse {

    private String approvarTeamName;

    private String approvarName;

    private int approvalOrder;

    private String approvalComment;

    private String approvalStateText;

    public ApprovalResponse(DocumentApproval approvar) {

        this.approvarTeamName = approvar.getApprover().getTeam().getName();

        this.approvarName = approvar.getApprover().getName();

        this.approvalOrder = approvar.getApprovalOrder();

        this.approvalComment = approvar.getApprovalComment();

        this.approvalStateText = approvar.getApprovalState().getName();
    }
}
