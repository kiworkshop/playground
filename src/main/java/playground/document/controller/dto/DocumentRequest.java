package playground.document.controller.dto;

import java.util.List;

import lombok.Getter;
import playground.document.type.Category;

@Getter
public class DocumentRequest {

    private String title;
    private Category category;
    private String contents;
    private Long drafterId;
    private List<Long> approverIds;
}
