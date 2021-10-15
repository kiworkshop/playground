package playground.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Document {

    private Long id;
    private String title;
    private String category;
    private String contents;
    private int userId;
    private String approvalState;
    private String userName;
    private String categoryText;
    private String approvalStateText;


}
