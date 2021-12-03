package playground.domain.document;

import lombok.*;
import playground.domain.user.User;
import playground.common.type.ApprovalState;
import playground.common.type.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static playground.common.type.ApprovalState.DRAFTING;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "document")
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Lob
    private String contents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalState approvalState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User drafter;

    @OneToMany(
        mappedBy = "document", fetch = FetchType.LAZY,
        cascade = CascadeType.ALL, orphanRemoval = true
    )
    private List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Builder
    public Document(String title, Category category, String contents, ApprovalState approvalState, User drafter) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approvalState = approvalState;
        this.drafter = drafter;
    }

    public void createApprovals(List<User> orderedApprovers) {
        IntStream.range(0, orderedApprovers.size())
            .mapToObj(index -> DocumentApproval.builder()
                .approver(orderedApprovers.get(index))
                .approvalState(DRAFTING)
                .approvalOrder(index + 1)
                .build()
            )
            .forEach(this::addDocumentApproval);
    }

    public void addDocumentApproval(DocumentApproval documentApproval) {
        documentApproval.setDocument(this);
        this.documentApprovals.add(documentApproval);
    }
}