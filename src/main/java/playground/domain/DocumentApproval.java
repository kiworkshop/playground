package playground.domain;

import lombok.Builder;
import lombok.Getter;
import playground.constants.ApprovalState;

@Builder
@Getter
public class    DocumentApproval {

    User approver;
    String approvalComment;
    ApprovalState approvalState;
    int approvalOrder;
}
