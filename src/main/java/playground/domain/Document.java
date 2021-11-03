package playground.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Document {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private Long drafterId;
    private ApprovalState approvalState;

    @Builder
    private Document(final String title, final String category,
                     final String contents, final Long drafterId) {
        this.title = title;
        this.category = Category.valueOf(category);
        this.contents = contents;
        this.drafterId = drafterId;
        this.approvalState = ApprovalState.DRAFTING;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Document document = (Document) o;
        return Objects.equals(id, document.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}