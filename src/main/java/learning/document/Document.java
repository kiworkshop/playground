package learning.document;

import learning.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private User drafter;
    private ApprovalState approvalState;
    @Builder.Default
    private List<DocumentApproval> documentApprovals = new LinkedList<>();

    public void addApprovers(List<User> users) {
        users.forEach(user -> {
            DocumentApproval documentApproval = DocumentApproval.builder()
                .approver(user)
                .approvalState(ApprovalState.DRAFTING)
                .approvalOrder(1 + documentApprovals.size())
                .build();

            documentApprovals.add(documentApproval);
        });
    }

    public void approveBy(User approver, String approvalComment) {
        documentApprovals.stream()
            .filter(documentApproval -> documentApproval.getApprover().equals(approver))
            .forEach(documentApproval -> documentApproval.update(approvalComment, ApprovalState.APPROVED));
    }

    public ApprovalState getApprovalState() {
        if (allApproved()) {
            return ApprovalState.APPROVED;
        }
        if (canceled()) {
            return ApprovalState.CANCELED;
        }
        return ApprovalState.DRAFTING;
    }

    private boolean allApproved() {
        return documentApprovals.stream()
            .allMatch(documentApproval -> ApprovalState.APPROVED.equals(documentApproval.getApprovalState()));
    }

    private boolean canceled() {
        return documentApprovals.stream()
            .anyMatch(documentApproval -> ApprovalState.CANCELED.equals(documentApproval.getApprovalState()));
    }
}

