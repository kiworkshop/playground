package playground.service.document.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.domain.document.vo.Category;
import playground.domain.user.User;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateDocumentRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String category;

    @NotBlank
    private String contents;

    @Min(value = 1)
    private Long drafterId;

    @NotEmpty
    private List<Long> approverIds;

    public CreateDocumentRequest(final String title, final String category, final String contents,
                                 final Long drafterId, final List<Long> approverIds) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafterId = drafterId;
        this.approverIds = approverIds;
    }

    public Document toDocument(final User drafter) {
        return Document.builder()
                .title(title)
                .category(Category.valueOf(category))
                .contents(contents)
                .drafter(drafter)
                .build();
    }
}
