package learning;

import lombok.Builder;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Builder
public class DocumentApprovals {
    private final List<DocumentApproval> approvers = new LinkedList<>();
}
