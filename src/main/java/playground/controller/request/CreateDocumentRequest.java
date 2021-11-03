package playground.controller.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.Document;
import playground.domain.DocumentApproval;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.Collections;
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

    public Document toDocument() {
        return Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafterId(drafterId)
                .build();
    }

    public List<DocumentApproval> toDocumentApprovals(final Long documentId) {
        ArrayList<DocumentApproval> documentApprovals = new ArrayList<>();

        for (int i = 0; i < approverIds.size(); i++) {
            DocumentApproval documentApproval = DocumentApproval.of(approverIds.get(i), documentId, i);
            documentApprovals.add(documentApproval);
        }

        return Collections.unmodifiableList(documentApprovals);
    }
}
