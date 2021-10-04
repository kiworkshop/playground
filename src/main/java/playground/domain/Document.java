package playground.domain;

import lombok.Builder;
import lombok.Getter;
import playground.constants.ApprovalState;
import playground.constants.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@Getter
public class Document {

    Long id;
    String title;
    Category category;
    String contents;
    User drafter;
    ApprovalState approvalState;
    List<DocumentApproval> documentApprovals;

    public void addApprovers(List<User> approvers) {
        documentApprovals = new ArrayList<>();
        approvalState = ApprovalState.DRAFTING;

        for (User approver : approvers) {
            DocumentApproval documentApproval = DocumentApproval.builder()
                    .approver(approver)
                    .approvalState(ApprovalState.DRAFTING)
                    .approvalOrder(documentApprovals.size() + 1)
                    .build();

            documentApprovals.add(documentApproval);
        }
    }

    public void approveBy(User approver, String approvalComment) {
        approverCheck(approver);
        for (int i = 0; i < documentApprovals.size(); i++) {
            if (documentApprovals.get(i).getApprover().equals(approver)) {
                checkPriorApprovals(i);
                documentApprovals.get(i).setApprovalComment(approvalComment);
                if (approvalComment.equals("결재 승인합니다.") || approvalComment.equals("확인했습니다.")) {
                    documentApprovals.get(i).setApprovalState(ApprovalState.APPROVED);
                }
            }
        }
        checkAllApprovals();

    }

    private void checkAllApprovals() {
        for (DocumentApproval documentApproval : documentApprovals) {
            if (documentApproval.getApprovalState() != ApprovalState.APPROVED) {
                return;
            }
        }
        approvalState = ApprovalState.APPROVED;
    }

    private void checkPriorApprovals(int approvalIndex) {
        for (int i=0; i<approvalIndex; i++) {
            if(documentApprovals.get(i).getApprovalState() != ApprovalState.APPROVED) {
                throw new IllegalArgumentException("우선순위 결재가 아직 처리되지 않았습니다.");
            }
        }
    }

    private void approverCheck(User approver) {
        Optional<DocumentApproval> matchApproval = documentApprovals.stream()
                .filter(approval -> approval.getApprover() == approver)
                .findAny();

        if(matchApproval.isEmpty()) {
            throw new IllegalArgumentException("결재 권한이 없습니다.");
        }
    }
}
