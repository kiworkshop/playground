package learning.document;

import learning.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

import static learning.document.ApprovalState.DRAFTING;

@Getter
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private User drafter;
    private ApprovalState approvalState;
    private DocumentApprovals documentApprovals = new DocumentApprovals();

    @Builder
    private Document(Long id, String title, Category category, String contents, User drafter, List<User> approvers) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
        this.approvalState = DRAFTING;

        addApprovers(approvers);
    }

    private void addApprovers(List<User> users) {
        users.forEach(user -> {
            DocumentApproval documentApproval = DocumentApproval.of(user, 1 + documentApprovals.size());
            documentApprovals.add(documentApproval);
        });
    }

    public void approveBy(User user, String approvalComment) {
        if (!this.documentApprovals.isApprover(user)) {
            throw new IllegalArgumentException("");
        }

        DocumentApproval documentApproval = findCurrentOrderDocumentApproval();
        if (!documentApproval.isOwnedBy(user)) {
            throw new IllegalArgumentException();
        }

        documentApproval.update(ApprovalState.APPROVED, approvalComment);

        if (allApproved()) {
            this.approvalState = ApprovalState.APPROVED;
        }
    }

    private DocumentApproval findCurrentOrderDocumentApproval() {
        return documentApprovals.getApprovals().stream()
            .filter(documentApproval -> DRAFTING.equals(documentApproval.getApprovalState()))
            .min(Comparator.comparingInt(DocumentApproval::getApprovalOrder))
            .orElseThrow(() -> new IllegalArgumentException());
        // TODO: 2021/10/09 적절한 exception 정의 및 테스트
    }

    private boolean allApproved() {
        return documentApprovals.getApprovals().stream()
            .allMatch(documentApproval -> ApprovalState.APPROVED.equals(documentApproval.getApprovalState()));
    }

    private boolean canceled() {
        return documentApprovals.getApprovals().stream()
            .anyMatch(documentApproval -> ApprovalState.CANCELED.equals(documentApproval.getApprovalState()));
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return this.documentApprovals.getApprovals();
    }
}

