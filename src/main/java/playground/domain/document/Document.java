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
import static lombok.AccessLevel.PROTECTED;
import static playground.domain.document.approval.ApprovalState.DRAFTING;

@Getter
@Table(name = "document")
@Entity
@NoArgsConstructor(access = PROTECTED)
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
    @JoinColumn(name = "drafter_id", foreignKey = @ForeignKey(name = "fk_document_drafter"))
    private User drafter;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

    @OneToMany(mappedBy = "document", cascade = ALL, orphanRemoval = true)
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

    public void createApprovals(List<User> aprovers) {
        for (int i = 0; i < aprovers.size(); i++) {
            DocumentApproval approval = DocumentApproval.builder()
                    .approver(aprovers.get(i))
                    .approvalOrder(i + 1)
                    .build();

            approval.upDateDocument(this);
            this.documentApprovals.add(approval);
        }
    }
}
