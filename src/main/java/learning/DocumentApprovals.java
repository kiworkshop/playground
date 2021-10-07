package learning;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public class DocumentApprovals {
    private final List<DocumentApproval> approvers = new LinkedList<>();

    public void add(DocumentApproval documentApproval) {
        this.approvers.add(documentApproval);
    }
}
