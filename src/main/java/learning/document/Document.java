package learning.document;

import learning.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
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
    private List<DocumentApproval> documentApprovals = new ArrayList<>();

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

    public void approveBy(User user, String approvalComment) {
        if (!isApprover(user)) {
            throw new IllegalArgumentException();
        }

        DocumentApproval documentApproval = findCurrentOrderDocumentApproval();
        if (!documentApproval.isOwnedBy(user)) {
            throw new IllegalArgumentException();
        }

        documentApproval.update(ApprovalState.APPROVED, approvalComment);
    }

    private boolean isApprover(User user) {
        return this.documentApprovals.stream()
            .map(DocumentApproval::getApprover)
            .anyMatch(approver -> approver.equals(user));
    }

    private DocumentApproval findCurrentOrderDocumentApproval() {
        return this.documentApprovals.stream()
            .filter(documentApproval -> ApprovalState.DRAFTING.equals(documentApproval.getApprovalState()))
            .min(Comparator.comparingInt(DocumentApproval::getApprovalOrder))
            .orElseThrow(() -> new IllegalArgumentException());
        // TODO: 2021/10/09 적절한 exception 정의 및 테스트
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

