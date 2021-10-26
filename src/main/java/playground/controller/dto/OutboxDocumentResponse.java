package playground.controller.dto;

import playground.type.ApprovalState;
import playground.type.Category;

public class OutboxDocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private ApprovalState approvalState;
    private String categoryText;
    private String approvalStateText;
}
