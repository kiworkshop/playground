package playground.domain.document.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.entity.Document;

@Getter
@Builder
public class BoxDocument {

    private final Long id;
    private final String title;
    private final String category;
    private final String categoryText;
    private final String approvalState;
    private final String approvalStateText;

    public static BoxDocument of(Document document) {
        return BoxDocument.builder()
                .id(document.getId())
                .title(document.getTitle())
                .category(document.getCategory().name())
                .categoryText(document.getCategory().getText())
                .approvalState(document.getApprovalState().name())
                .approvalStateText(document.getApprovalState().getText())
                .build();
    }
}
