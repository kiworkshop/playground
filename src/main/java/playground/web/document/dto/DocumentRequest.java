package playground.web.document.dto;

import java.util.List;

import lombok.Getter;
import playground.type.Category;

@Getter
public class DocumentRequest {

    private String title;
    private Category category;
    private String contents;

    private Long drafterId;
    private List<Long> approverIds;
}
