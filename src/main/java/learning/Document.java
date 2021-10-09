package learning;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

import static learning.ApprovalState.APPROVED;
import static learning.ApprovalState.CANCELED;
import static learning.ApprovalState.DRAFTING;

@Getter
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private User drafter;
    private ApprovalState approvalState;
    private final DocumentApprovals documentApprovals = new DocumentApprovals();

    @Builder
    private Document(Long id, String title, Category category, String contents, User drafter, List<User> approvers) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;

        addApprovers(approvers);
        this.approvalState = decideDocumentState();
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals.getApprovals();
    }

    public void approveBy(User approver, String approvalComment) {
        documentApprovals.approveBy(approver, approvalComment);

        this.approvalState = decideDocumentState();
    }

    private void addApprovers(List<User> approvers) {
        for (int index = 0; index < approvers.size(); index++) {
            User approver = approvers.get(index);
            DocumentApproval documentApproval = DocumentApproval.of(approver, index + 1);

            documentApprovals.add(documentApproval);
        }
    }

    private ApprovalState decideDocumentState() {
        if (documentApprovals.hasAllApproved()) {
            return APPROVED;
        }

        if (documentApprovals.hasCanceled()) {
            return CANCELED;
        }

        return DRAFTING;
    }

}
