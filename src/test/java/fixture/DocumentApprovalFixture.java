package fixture;

import playground.domain.document.ApprovalState;
import playground.domain.documentapproval.DocumentApproval;
import playground.domain.user.User;

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
