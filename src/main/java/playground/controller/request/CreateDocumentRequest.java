package playground.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private long drafterId;

    @NotEmpty
    private List<Long> approvalIds;

    public CreateDocumentRequest(final String title, final String category, final String contents,
                                 final long drafterId, final List<Long> approvalIds) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafterId = drafterId;
        this.approvalIds = approvalIds;
    }
}
