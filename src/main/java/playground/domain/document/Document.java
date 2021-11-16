package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

import static playground.domain.document.ApprovalState.*;

@Getter
public class Document {

    private final Long id;
    private final String title;
    private final Category category;
    private final String contents;
    private final User drafter;
    private ApprovalState approvalState = DRAFTING;
    private final DocumentApprovals documentApprovals = new DocumentApprovals();
    private LocalDateTime insertDate;
    private LocalDateTime updateDate;

    @Builder(builderClassName = "DocumentBuilder", builderMethodName = "create")
    private Document(Long id, String title, Category category, String contents, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
    }

    @Builder(builderClassName = "DocumentAllBuilder", builderMethodName = "rowMapper")
    private Document(Long id, String title, Category category, String contents, User drafter, ApprovalState approvalState, LocalDateTime insertDate, LocalDateTime updateDate) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
        this.approvalState = approvalState;
        this.insertDate = insertDate;
        this.updateDate = updateDate;
    }

    public void addApprovers(List<User> approvers) {
        for (int i = 0; i < approvers.size(); i++) {
            documentApprovals.add(new DocumentApproval(approvers.get(i), (i + 1)));
        }
    }

    public void approveBy(User approver, String approvalComment) {
        DocumentApproval currentApproval = documentApprovals.findBy(approver);
        currentApproval.approve(approvalComment);

        if (documentApprovals.allAproved()) {
            approvalState = APPROVED;
        }
    }

    public void rejectBy(User approver, String approvalComment) {
        DocumentApproval currentApproval = documentApprovals.findBy(approver);
        currentApproval.reject(approvalComment);
        approvalState = CANCELED;
    }

    public ApprovalState getApprovalState() {
        return approvalState;
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals.getDocumentApprovals();
    }
}