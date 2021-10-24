package playground.domain.document.entity;

import lombok.Builder;
import lombok.Getter;
import playground.domain.user.entity.User;

import java.sql.Timestamp;
import java.util.Objects;

@Builder
@Getter
public class Document {

    private Long id;
    private String title;
    private String contents;
    private User drafter;
    private Category category;
    private ApprovalState approvalState;
    private Timestamp createdAt;
//    private DocumentApprovals approvals;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Document document = (Document) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
