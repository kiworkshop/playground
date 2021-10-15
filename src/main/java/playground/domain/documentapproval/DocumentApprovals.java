package playground.domain.documentapproval;

import playground.domain.user.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DocumentApprovals {

    private final List<DocumentApproval> documentApprovals = new ArrayList<>();

    public void add(final DocumentApproval documentApproval) {
        if (documentApproval != null) {
            documentApprovals.add(documentApproval);
        }
    }

    public List<DocumentApproval> list() {
        return Collections.unmodifiableList(documentApprovals);
    }

    public void approveBy(final User approver, final String approvalComment) {
        checkRegisteredApprover(approver);

        for (DocumentApproval documentApproval : documentApprovals) {
            checkPreviousDocumentApproval(documentApproval, approver);

            if (documentApproval.isNotApproved()) {
                documentApproval.changeStateToApproved(approvalComment);
                break;
            }
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

    public boolean isAllApproved() {
        return documentApprovals.stream()
                .allMatch(DocumentApproval::isApproved);
    }

    public boolean isNotEmpty() {
        return !documentApprovals.isEmpty();
    }
}
