package learning;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

import static learning.ApprovalState.*;

@Getter
public class DocumentApprovals {
    private final List<DocumentApproval> documentApprovals = new LinkedList<>();

    public void add(DocumentApproval documentApproval) {
        this.documentApprovals.add(documentApproval);
    }

    public DocumentApproval findBy(User approver) {
        return documentApprovals.stream()
                .filter(approval -> approval.getApproverId() == approver.getId())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결재 요청 대상자가 아닙니다."));
    }

    public boolean allAprove() {
        return documentApprovals.stream()
                .allMatch(documentApproval -> documentApproval.getApprovalState() == APPROVED);
    }
}
