package learning.document;

import learning.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private User drafter;
    @Builder.Default
    private List<DocumentApproval> documentApprovals = new ArrayList<>();

    public void addApprovers(List<User> users) {
        users.forEach(user -> {
            DocumentApproval documentApproval = DocumentApproval.builder()
                .approver(user)
                .approvalState(ApprovalState.DRAFTING)
                .build();

            documentApprovals.add(documentApproval);
        });
    }

    public void approveBy(User approver, String approvalComment) {
        documentApprovals.stream()
            .filter(documentApproval -> documentApproval.getApprover().equals(approver))
            .forEach(documentApproval -> documentApproval.update(approvalComment, ApprovalState.APPROVED));
    }

    public boolean getApprovalState() {
        return documentApprovals.stream()
            .allMatch(documentApproval -> ApprovalState.APPROVED.equals(documentApproval.getApprovalState()));
    }
}

