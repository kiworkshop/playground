package playground.dto;

import learning.Category;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DocumentRequest {

    private String title;
    private Category category;
    private String contents;
    private Long drafterId;
    private List<Long> approverIds;

}
