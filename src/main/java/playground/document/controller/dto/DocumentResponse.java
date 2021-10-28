package playground.document.controller.dto;

import playground.document.type.ApprovalState;
import playground.document.type.Category;

public class DocumentResponse {

    private Long id;
    private String title;
    private Category category;
    private String contents;
    private Long userId;
    private ApprovalState approvalState;
    private String userName;
    private String categoryText;
    private String approvalStateText;
}
