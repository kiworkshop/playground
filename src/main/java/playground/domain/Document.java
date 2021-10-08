package playground.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class Document {
    private Long id;
    private String title;
    private Category category;
    private String contents;
    private User drafter;
    private ApprovalState approvalState = ApprovalState.DRAFTING;
    private List<User> approvals;
    private List<DocumentApproval> documentApprovals = new ArrayList<DocumentApproval>();
    private int apprIndex;

    @Builder
    public Document(Long id, String title, Category category, String contents, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
    }

    public void addApprovers(List<User> approvals) {
        this.approvals = approvals;
        AtomicInteger index = new AtomicInteger();
        index.getAndIncrement();
        apprIndex = 0;
        approvals.stream().forEach((approver) -> addDocumetApprovals(approver, index.getAndIncrement()));
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals;
    }

    private void addDocumetApprovals(User approver, int index) {
        documentApprovals.add(
                DocumentApproval.builder()
                        .approver(approver)
                        .approvalState(ApprovalState.DRAFTING)
                        .approvalOrder(index)
                        .build()
        );
    }

    public void approveBy(User approver, String approvalComment) {
        checkApproverTurn(approver);
        documentApprovals.set(apprIndex, documentApprovals.get(apprIndex).approveBy(approver, approvalComment));
        apprIndex++;

        if (apprIndex >= documentApprovals.size()) {
            approvalState = ApprovalState.APPROVED;
        }
    }

    public ApprovalState getApprovalState() {
        return approvalState;
    }

    private boolean checkApproverTurn(User approver) {
        if (!documentApprovals.get(apprIndex).getApprover().getId().equals(approver.getId())) {
            throw new IllegalArgumentException();
        }
        return true;
    }
}
