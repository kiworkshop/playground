package fixture;

import learning.ApprovalState;
import learning.DocumentApproval;
import learning.User;

public class DocumentApprovalFixture {

    public static DocumentApproval create(final User approver, final ApprovalState approvalState,
                                          final Integer approvalOrder, final String approvalComment) {
        return DocumentApproval.builder()
                .approver(approver)
                .approvalState(approvalState)
                .approvalOrder(approvalOrder)
                .approvalComment(approvalComment)
                .build();
    }
}
