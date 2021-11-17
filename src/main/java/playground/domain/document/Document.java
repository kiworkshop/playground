package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.approval.ApprovalState;
import playground.domain.document.approval.DocumentApproval;
import playground.domain.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static playground.domain.document.approval.ApprovalState.DRAFTING;

@Getter
@Entity
@NoArgsConstructor
public class Document extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Lob
    private String contents;

    @ManyToOne(fetch = LAZY)
    private User drafter;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

    @OneToMany(cascade = ALL)
    private List<DocumentApproval> documentApprovals;

    @Builder
    public Document(String title, Category category, String contents, User drafter) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
        this.approvalState = DRAFTING;
        this.documentApprovals = new ArrayList<>();
    }

    public void addDocumentApprovals(DocumentApproval documentApproval) {
        this.documentApprovals.add(documentApproval);
        documentApproval.setDocument(this);
    }
}
