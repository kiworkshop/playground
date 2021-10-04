package playground.domain;

import lombok.Builder;
import lombok.Getter;
import playground.constants.ApprovalState;
import playground.constants.Category;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class Document {

    Long id;
    String title;
    Category category;
    String contents;
    User drafter;
    ApprovalState approvalState;
    List<DocumentApproval> documentApprovals;

    public void addApprovers(List<User> approvers) {
        documentApprovals = new ArrayList<>();
        approvalState = ApprovalState.DRAFTING;

        for(User approver : approvers) {
            DocumentApproval documentApproval = DocumentApproval.builder()
                    .approver(approver)
                    .approvalState(ApprovalState.DRAFTING)
                    .approvalOrder(documentApprovals.size() + 1)
                    .build();

            documentApprovals.add(documentApproval);
        }
    }

    public void approveBy(User approver, String approvalComment) {
        
    }
}
