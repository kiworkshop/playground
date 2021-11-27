package playground.web.document.dto;

import lombok.Getter;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.user.User;

import java.util.List;

import static playground.domain.document.ApprovalState.DRAFTING;

@Getter
public class DocumentCreateRequest {

    private String title;
    private Category category;
    private String contents;

    private Long drafterId;
    private List<Long> approverIds;

    public Document toEntity(User drafter, List<User> orderedApprovers) {
        Document document = Document.builder()
                .title(title)
                .category(category)
                .contents(contents)
                .drafter(drafter)
                .approvalState(DRAFTING)
                .build();

        document.createApprovals(orderedApprovers);
        return document;
    }

}
