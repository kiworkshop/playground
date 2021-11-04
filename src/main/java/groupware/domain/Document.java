package groupware.domain;

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
    private String content;
    private User drafter;
    private ApprovalState approvalState = ApprovalState.DRAFTING;
    private List<DocumentApproval> documentApprovals = new ArrayList<DocumentApproval>();
    private int approvalIndex;

    @Builder
    public Document(Long id, String title, Category category, String content, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.content = content;
        this.drafter = drafter;
    }

    public void addApprovers(List<User> approvals) {
        AtomicInteger index = new AtomicInteger();
        index.getAndIncrement();
        approvalIndex = 0;
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
        checkApprovalTurn(approver);
        documentApprovals.set(approvalIndex, documentApprovals.get(approvalIndex).approveBy(approver, approvalComment));
        approvalIndex++;

        if (approvalIndex >= documentApprovals.size()) {
            approvalState = ApprovalState.APPROVED;
        }
    }

    public ApprovalState getApprovalState() {
        return approvalState;
    }

    private void checkApprovalTurn(User approver) {

        if (!documentApprovals.get(approvalIndex).getApprover().getId().equals(approver.getId())) {
            throw new IllegalArgumentException();
        }
    }
}
