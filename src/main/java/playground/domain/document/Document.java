package playground.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.domain.user.User;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "contents", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drafter_id")
    private User drafter;

    @Embedded
    private DocumentApprovals documentApprovals = new DocumentApprovals();

    @Enumerated(value = EnumType.STRING)
    @Column(name = "approval_state", nullable = false)
    private ApprovalState approvalState;

    @Builder
    private Document(final String title, final Category category,
                     final String contents, final User drafter) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
        this.approvalState = ApprovalState.DRAFTING;
    }

    public void enrollApprovals(final List<User> approvers) {
        documentApprovals.enroll(approvers, this);
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return Collections.unmodifiableList(documentApprovals.getDocumentApprovals());
    }
}
