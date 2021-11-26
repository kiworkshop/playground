package playground.domain.document;

import lombok.*;
import playground.domain.user.User;
import playground.type.ApprovalState;
import playground.type.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Table(name = "document")
@Entity
public class Document {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String contents;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;

    @ManyToOne(fetch = FetchType.LAZY)
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

    public void addDocumentApproval(DocumentApproval documentApproval) {
        documentApproval.setDocument(this);
        this.documentApprovals.add(documentApproval);
    }
}