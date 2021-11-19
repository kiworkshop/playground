package playground.domain.document;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "DOCUMENT")
@Getter
@NoArgsConstructor
public class Document {

    @Id
    @Column(name = "ID")
    private Long id;

    private String title;
    private Category category;
    private String contents;

    @Enumerated(EnumType.STRING)
    private ApprovalState approvalState;
    private Long drafterId;

    @Builder
    private Document(Long id, String title, Category category, String contents, ApprovalState approvalState, Long drafterId) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.approvalState = approvalState;
        this.drafterId = drafterId;
    }

}
