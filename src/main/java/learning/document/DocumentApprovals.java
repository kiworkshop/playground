package learning.document;

import learning.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class DocumentApprovals {

    private List<DocumentApproval> approvals = new ArrayList<>();

    public DocumentApprovals(List<DocumentApproval> approvals) {
        this.approvals = approvals;
    }

    public void add(DocumentApproval documentApproval) {

    }

    public int size() {
        return this.approvals.size();
    }

    public boolean isApprover(User user) {
        return this.approvals.stream()
            .map(DocumentApproval::getApprover)
            .anyMatch(approver -> approver.equals(user));
    }
}
