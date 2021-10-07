package learning;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private User drafter;
    private ApprovalState approvalState;
    private List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Builder
    public Document(final Long id, final String title, final Category category,
                    final String contents, final User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approvalState = ApprovalState.DRAFTING;
        this.drafter = drafter;
    }

    public void addApprovers(final List<User> approvers) {
        int currentDocumentApprovalOrder = this.documentApprovals.size();

        for (User approver : approvers) {
            currentDocumentApprovalOrder += 1;
            DocumentApproval documentApproval = DocumentApproval.builder()
                    .approver(approver)
                    .approvalState(ApprovalState.DRAFTING)
                    .approvalOrder(currentDocumentApprovalOrder)
                    .build();

            this.documentApprovals.add(documentApproval);
        }
    }

    public void approveBy(final User approver, final String approvalComment) {
        checkRegisteredApprover(approver);
        boolean isLastApprover = false;

        for (DocumentApproval documentApproval : documentApprovals) {
            checkPreviousDocumentApproval(documentApproval, approver);

            if (documentApproval.isNotApproved()) {
                documentApproval.changeStateToApproved(approvalComment);
                isLastApprover = documentApproval.isLastApproval(documentApprovals.size());
                break;
            }
        }

        if (isLastApprover) {
            approvalState = ApprovalState.APPROVED;
        }
    }

    private void checkRegisteredApprover(final User approver) {
        if (isNotRegisteredApprover(approver)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isNotRegisteredApprover(final User approver) {
        return documentApprovals.stream()
                .allMatch(documentApproval -> documentApproval.hasNotSameApprover(approver));
    }

    private void checkPreviousDocumentApproval(final DocumentApproval documentApproval, final User approver) {
        if (documentApproval.isNotApproved() && documentApproval.hasNotSameApprover(approver)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Document document = (Document) o;
        return id.equals(document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
