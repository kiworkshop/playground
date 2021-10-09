package learning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentApprovals {

    private final List<DocumentApproval> approvals = new ArrayList<>();

    public List<DocumentApproval> getApprovals() {
        return new ArrayList<>(approvals);
    }

    public void add(DocumentApproval documentApproval) {
        if (documentApproval != null) {
            this.approvals.add(documentApproval);
        }
    }

    public void approveBy(User approver, String approvalComment) {
        DocumentApproval targetApproval = findTargetApproval(approver);
        Integer targetOrder = targetApproval.getApprovalOrder();

        List<DocumentApproval> previousApprovals = findPreviousApprovals(targetOrder);
        if (hasProgressing(previousApprovals)) {
            throw new IllegalArgumentException("현재 결재할 순서가 아닙니다.");
        }

        targetApproval.approve(approvalComment);
    }

    public boolean hasAllApproved() {
        return approvals.stream()
                .allMatch(DocumentApproval::isApproved);
    }

    public boolean hasCanceled() {
        return approvals.stream()
                .anyMatch(DocumentApproval::isCanceled);
    }

    private DocumentApproval findTargetApproval(User approver) {
        return approvals.stream()
                .filter(approval -> approval.isSameApprover(approver))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 문서의 결재 대상자가 아닙니다."));
    }

    private List<DocumentApproval> findPreviousApprovals(Integer targetOrder) {
        return this.approvals.stream()
                .sorted(Comparator.comparing(DocumentApproval::getApprovalOrder))
                .collect(Collectors.toList())
                .subList(0, targetOrder - 1);
    }

    private boolean hasProgressing(List<DocumentApproval> approvals) {
        return approvals.stream()
                .anyMatch(DocumentApproval::isProgressing);
    }

}
