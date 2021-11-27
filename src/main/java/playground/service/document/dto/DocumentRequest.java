package playground.service.document.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.user.User;

import java.util.List;

@Getter
@Builder
public class DocumentRequest {

    private String title;
    private Category category;
    private String contents;
    private Long drafterId;
    private List<Long> approverIds;

    public Document toDocument(User drafter) {
        return Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafter(drafter)
                .build();
    }
}
