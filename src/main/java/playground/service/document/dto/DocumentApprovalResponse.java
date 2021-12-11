package playground.service.document.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.DocumentApproval;
import playground.domain.user.User;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class DocumentApprovalResponse implements Comparable<DocumentApprovalResponse> {

    private final String approverTeamName;
    private final String approverName;
    private final ApprovalState approvalState;
    private final Integer approvalOrder;
    private final String approvalComment;

    public static DocumentApprovalResponse of(DocumentApproval documentApproval) {
        User approver = documentApproval.getApprover();

        return new DocumentApprovalResponse(
                approver.getTeamName(),
                approver.getName(),
                documentApproval.getApprovalState(),
                documentApproval.getApprovalOrder(),
                documentApproval.getApprovalComment()
        );
    }

    @Override
    public int compareTo(DocumentApprovalResponse o) {
        return this.approvalOrder - o.approvalOrder;
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
