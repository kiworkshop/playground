package playground.domain.document;

import lombok.Getter;
import playground.domain.user.User;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
public class DocumentApprovals {

    private final List<DocumentApproval> documentApprovals = new LinkedList<>();

    public void add(DocumentApproval documentApproval) {
        this.documentApprovals.add(documentApproval);
    }

    public DocumentApproval findBy(User approver) {
        DocumentApproval targetApproval = findTarget(approver);

        if (findNextApproval() == targetApproval) {
            return targetApproval;
        }
        throw new IllegalArgumentException("현재 결재 순서가 아닙니다.");
    }

    private DocumentApproval findTarget(User approver) {
        return documentApprovals.stream()
                .filter(approval -> Objects.equals(approval.getApproverId(), approver.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결재 요청 대상자가 아닙니다."));
    }

    private DocumentApproval findNextApproval() {
        return documentApprovals.stream()
                .sorted(Comparator.comparing(DocumentApproval::getApprovalOrder))
                .filter(approval -> approval.getApprovalState() == DRAFTING)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결재중인 문서가 없습니다."));
    }

    public boolean allAproved() {
        return documentApprovals.stream()
                .allMatch(this::isApproved);
    }

    private boolean isApproved(DocumentApproval documentApproval) {
        return documentApproval.isApproved();
    }
}
