package playground.domain.document.dto.param;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.dto.AddDocumentRequest;

import static playground.domain.document.entity.ApprovalState.DEFAULT_APPROVAL_STATE_TEXT;

@Getter
@Builder
public class AddDocumentParam {

    private final String title;
    private final String contents;
    private final Long drafterId;
    private final String categoryText;
    private final String approvalStateText;

    public static AddDocumentParam of(AddDocumentRequest addDocumentRequest) {
        return AddDocumentParam.builder()
                .title(addDocumentRequest.getTitle())
                .contents(addDocumentRequest.getContents())
                .drafterId(addDocumentRequest.getDrafterId())
                .categoryText(addDocumentRequest.getCategory())
                .approvalStateText(DEFAULT_APPROVAL_STATE_TEXT)
                .build();
    }
}
