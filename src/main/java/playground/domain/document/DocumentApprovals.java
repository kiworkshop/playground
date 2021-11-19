package playground.domain.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DocumentApprovals {

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentApproval> documentApprovals = new ArrayList<>();

    public void enroll(final List<User> approvers, final Document document) {
        checkEmpty();

        for (int i = 0; i < approvers.size(); i++) {
            DocumentApproval documentApproval = DocumentApproval.of(approvers.get(i), document, i + 1);
            documentApprovals.add(documentApproval);
        }
    }

    private void checkEmpty() {
        if (!documentApprovals.isEmpty()) {
            throw new IllegalStateException("결재자 추가 등록이 불가능합니다.");
        }
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return Collections.unmodifiableList(documentApprovals);
    }
}
