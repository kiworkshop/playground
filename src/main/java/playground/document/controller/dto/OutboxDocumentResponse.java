package playground.document.controller.dto;

import playground.document.type.ApprovalState;
import playground.document.type.Category;

public class OutboxDocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;
    private String categoryText;
    private String approvalStateText;
}
