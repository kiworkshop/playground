package learning;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static learning.ApprovalState.APPROVED;
import static learning.ApprovalState.DRAFTING;

@Getter
public class Document {
    private final long id;
    private final String title;
    private final Category category;
    private final String contents;
    private final User drafter;

    private ApprovalState approvalState = DRAFTING;
    private final DocumentApprovals documentApprovals = new DocumentApprovals();

    @Builder
    private Document(Long id, String title, Category category, String contents, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
    }

    public void addApprovers(List<User> approvers) {
        for (int i = 0; i < approvers.size(); i++) {
            documentApprovals.add(new DocumentApproval(approvers.get(i), (i + 1)));
        }
    }

    public void approveBy(User approver, String approvalComment) {
        DocumentApproval currentApproval = documentApprovals.findBy(approver);
        currentApproval.approve(approvalComment);

        if (documentApprovals.allAprove()) {
            approvalState = APPROVED;
        }
    }

    public ApprovalState getApprovalState() {
        return approvalState;
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals.getDocumentApprovals();
    }
}
