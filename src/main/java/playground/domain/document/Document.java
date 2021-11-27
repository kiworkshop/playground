package playground.domain.document;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Lob
    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalState approvalState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User drafter;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private final List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Builder
    private Document(String title, Category category, String contents, ApprovalState approvalState, User drafter) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approvalState = approvalState;
        this.drafter = drafter;
    }

    public void createApprovals(List<User> orderedApprovers) {
        for (int index = 0; index < orderedApprovers.size(); index++) {
            DocumentApproval approval = DocumentApproval.builder()
                    .approvalState(DRAFTING)
                    .approver(orderedApprovers.get(index))
                    .approvalOrder(index + 1)
                    .build();

            approval.updateDocument(this);
            this.documentApprovals.add(approval);
        }
    }

}
