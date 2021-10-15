package learning;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DocumentApprovals {

    private final List<DocumentApproval> approvals;

    private DocumentApprovals() {
        this.approvals = new ArrayList<>();
    }

    public static DocumentApprovals empty() {
        return new DocumentApprovals();
    }

    public void addApprovals(List<User> approvers) {
        List<DocumentApproval> documentApprovals =
                IntStream.range(0, approvers.size())
                        .mapToObj(index -> DocumentApproval.of(approvers.get(index), index + 1))
                        .collect(Collectors.toList());

        this.approvals.addAll(documentApprovals);
    }

    public List<DocumentApproval> getDocumentApprovals() {
        // only for test
        return new ArrayList<>(approvals);
    }

    public void approveBy(User approver, String approvalComment) {
        if (isNotRegistered(approver)) {
            throw new IllegalArgumentException("님은 결재 리스트에 없다");
        }

        DocumentApproval targetApproval = targetApproval();
        if (targetApproval.isNotFor(approver)) {
            throw new IllegalArgumentException("님 차례가 아니다");
        }

        targetApproval.approve(approvalComment);
    }

    private boolean isNotRegistered(User approver) {
        return approvals.stream()
                .noneMatch(documentApproval -> documentApproval.isFor(approver));
    }

    private DocumentApproval targetApproval() {
        return approvals.stream()
                .filter(DocumentApproval::isDrafting)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("결재할 문서가 없다"));
    }

    public boolean isAllApproved() {
        return approvals.stream()
                .allMatch(DocumentApproval::isApproved);
    }
}
