package playground.service.document.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.domain.document.vo.ApprovalState;
import playground.domain.document.vo.Category;
import playground.service.user.response.SelectUserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectDocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private ApprovalState approvalState;
    private SelectUserResponse drafter;
    private List<SelectApproverResponse> approvers;
    private String categoryText;
    private String approvalStateText;

    public SelectDocumentResponse(final Document document, final List<DocumentApproval> documentApprovals) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.contents = document.getContents();
        this.approvalState = document.getApprovalState();
        this.drafter = new SelectUserResponse(document.getDrafter());
        this.approvers = documentApprovals.stream()
                .map(SelectApproverResponse::new)
                .collect(Collectors.toList());
        this.categoryText = document.getCategory().getText();
        this.approvalStateText = document.getApprovalState().getText();
    }
}
