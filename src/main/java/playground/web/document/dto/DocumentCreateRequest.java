package playground.web.document.dto;

import lombok.Getter;
import playground.domain.document.Category;
import playground.domain.document.Document;

import java.util.List;

import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
public class DocumentCreateRequest {

    private String title;
    private Category category;
    private String contents;

    private Long drafterId;
    private List<Long> approverIds;

    public Document toEntity() {
        return Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafterId(drafterId)
                .approvalState(DRAFTING)
                .build();
    }

}
