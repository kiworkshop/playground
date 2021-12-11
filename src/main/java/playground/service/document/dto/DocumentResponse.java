package playground.service.document.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.ApprovalState;
import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.document.DocumentApproval;
import playground.service.user.dto.UserResponse;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class DocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private ApprovalState approvalState;
//    private Long userId;
//    private String userName;
    private UserResponse drafter;
    private List<DocumentApprovalResponse> approvers;

    public DocumentResponse(Document document) {
        this.id = document.getId();
        this.title = document.getTitle();
        this.category = document.getCategory();
        this.contents = document.getContents();
        this.approvalState = document.getApprovalState();

        this.drafter = new UserResponse(document.getDrafter());

        this.approvers = document.getDocumentApprovals().stream()
                .map(DocumentApprovalResponse::of)
                .sorted()
                .collect(Collectors.toList());
    }

    public String getCategoryText() {
        return category.getText();
    }

    public String getApprovalStateText() {
        return approvalState.getText();
    }

}
