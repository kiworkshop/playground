package learning;

import lombok.Builder;

import java.util.List;
@Builder
public class Document {
    private final long id;
    private final String title;
    private final Category category;
    private final String contents;
    private final User drafter;
    private DocumentApprovals documentApprovals;


    public void addApprovers(List<User> approvers) {

    }

    public void approveBy(User approver1, String approvalComment) {

    }

    public ApprovalState getApprovalState() {
        return ApprovalState.DRAFTING;
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals.getApprovers();
    }
}
