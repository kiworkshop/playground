package playground.domain;

import lombok.Builder;
import lombok.Getter;
import playground.constants.ApprovalState;
import playground.constants.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
public class Document {

    private final Long id;
    private String title;
    private Category category;
    private String contents;
    private final User drafter;
    private ApprovalState approvalState = ApprovalState.DRAFTING;
    private final List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Builder
    public Document(Long id, String title, Category category, String contents, User drafter) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
    }

    public void addApprovers(List<User> approvers) {

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
            DocumentApproval documentApproval = documentApprovals.get(i);

            if (documentApproval.identifyApprover(approver)) {
                checkPriorApprovalsRemainNotApproved(i);
                documentApproval.setApprovalComment(approvalComment);

                setApprovalStateByApprovalComment(approvalComment, documentApproval);
            }
        }
        checkAllApprovals();

    }

    private void setApprovalStateByApprovalComment(String approvalComment, DocumentApproval documentApproval) {
        if (approved(approvalComment)) {
            documentApproval.setApprovalState(ApprovalState.APPROVED);
        }
    }

    private boolean approved(String approvalComment) {
        return approvalComment.equals("결재 승인합니다.") || approvalComment.equals("확인했습니다.");
    }

    private void checkAllApprovals() {
        for (DocumentApproval documentApproval : documentApprovals) {
            if (documentApproval.isNotApproved()) {
                return;
            }
        }
        approvalState = ApprovalState.APPROVED;
    }

    private void checkPriorApprovalsRemainNotApproved(int approvalIndex) {
        for (int i = 0; i < approvalIndex; i++) {
            if (documentApprovals.get(i).isNotApproved()) {
                throw new IllegalArgumentException("우선순위 결재가 아직 처리되지 않았습니다.");
            }
        }
    }

    private void approverCheck(User approver) {
        Optional<DocumentApproval> matchApproval = documentApprovals.stream()
                .filter(approval -> approval.identifyApprover(approver))
                .findAny();

        if (matchApproval.isEmpty()) {
            throw new IllegalArgumentException("결재 권한이 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        Document document = (Document) o;
        return getId().equals(document.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
