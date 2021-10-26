package playground.controller.dto;

import playground.type.ApprovalState;
import playground.type.Category;

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
