package playground.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.documentapproval.DocumentApproval;
import playground.domain.documentapproval.DocumentApprovals;
import playground.domain.user.User;

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
    private final DocumentApprovals documentApprovals = new DocumentApprovals();

    @Builder
    private Document(final Long id, final String title, final Category category,
                     final String contents, final User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approvalState = ApprovalState.DRAFTING;
        this.drafter = drafter;
    }

    public void addApprovers(final List<User> approvers) {
        checkIfDocumentApprovalsIsEmpty();

        for (int i = 0; i < approvers.size(); i++) {
            User approver = approvers.get(i);
            DocumentApproval documentApproval = DocumentApproval.of(approver, i + 1);
            documentApprovals.add(documentApproval);
        }
    }

    private void checkIfDocumentApprovalsIsEmpty() {
        if (documentApprovals.isNotEmpty()) {
            throw new IllegalStateException();
        }
    }

    public void approveBy(final User approver, final String approvalComment) {
        documentApprovals.approveBy(approver, approvalComment);

        if (documentApprovals.isAllApproved()) {
            approvalState = ApprovalState.APPROVED;
        }
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals.list();
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
