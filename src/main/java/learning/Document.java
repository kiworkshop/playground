package learning;

import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class Document {

    @Getter
    private final Long id;
    @Getter
    private final String title;
    private final Category category;
    private final String contents;
    private final User drafter;

    private DocumentApprovals approvals;
    @Getter
    private ApprovalState approvalState;

    @Builder
    public Document(Long id, String title, Category category, String contents, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;

        this.approvals = DocumentApprovals.empty();
        this.approvalState = ApprovalState.DRAFTING;
    }

    public void addApprover(User approver) {
        addApprovers(Collections.singletonList(approver));
    }

    public void addApprovers(List<User> approvers) {
        this.approvals.addApprovals(approvers);
    }

    public List<DocumentApproval> getDocumentApprovals() {
        // only for test
        return approvals.getDocumentApprovals();
    }

    public void approveBy(User approver, String approvalComment) {
        approvals.approveBy(approver, approvalComment);

        if (approvals.isAllApproved()) {
            approvalState = approvalState.approve();
        }
    }
}
