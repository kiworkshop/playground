package playground.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import playground.constants.ApprovalState;

@Builder
@Getter
@Setter
public class DocumentApproval {

    User approver;
    String approvalComment;
    ApprovalState approvalState;
    int approvalOrder;
}
