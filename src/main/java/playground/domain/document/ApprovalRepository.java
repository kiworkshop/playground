package playground.domain.document;

public interface ApprovalRepository {

    void saveAll(DocumentApprovals documentApprovals);

    void save(DocumentApproval documentApproval);
}
