package playground.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String contents;

    @ManyToOne
    @JoinColumn(name = "drafter_id")
    private User drafter;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState = ApprovalState.DRAFTING;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentApproval> documentApprovals = new ArrayList<>();

    @Transient
    private int approvalIndex;

    @CreatedDate
    private Date createdAt;

    @Builder
    public Document(Long id, String title, Category category, String contents, User drafter, List<DocumentApproval> documentApprovals, Date createdAt) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafter = drafter;
        this.documentApprovals = documentApprovals;
        this.createdAt = createdAt;
    }

    public void addDocumentApproval(final DocumentApproval documentApproval){
        documentApprovals.add(documentApproval);
        documentApproval.setDocument(this);
    }

    public void addApprovers(List<User> approvals) {
        AtomicInteger index = new AtomicInteger();
        index.getAndIncrement();
        approvalIndex = 0;
        approvals.forEach(approver -> addDocumetApprovals(approver, index.getAndIncrement()));
    }

    public List<DocumentApproval> getDocumentApprovals() {
        return documentApprovals;
    }

    private void addDocumetApprovals(User approver, int index) {
        documentApprovals.add(
                DocumentApproval.builder()
                        .approver(approver)
                        .approvalState(ApprovalState.DRAFTING)
                        .approvalOrder(index)
                        .build()
        );
    }

    public void approveBy(User approver, String approvalComment) {
        checkApprovalTurn(approver);
        documentApprovals.set(approvalIndex, documentApprovals.get(approvalIndex).approveBy(approver, approvalComment));
        approvalIndex++;

        if (approvalIndex >= documentApprovals.size()) {
            approvalState = ApprovalState.APPROVED;
        }
    }


    public ApprovalState getApprovalState() {
        return approvalState;
    }

    private void checkApprovalTurn(User approver) {

        if (!documentApprovals.get(approvalIndex).getApprover().getId().equals(approver.getId())) {
            throw new IllegalArgumentException();
        }
    }
}
