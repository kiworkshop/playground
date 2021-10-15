package playground.domain;

import lombok.Builder;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class ApproveGenerationRequestDto {

    private String title;
    private String category;
    private String contents;
    private int drafterId;
    @Setter
    private List<Integer> approverIds = new ArrayList<>();

    @Builder
    private ApproveGenerationRequestDto(String title, String category, String contents, int drafterId) {
        this.title = title;
        this.category = category;
        this.contents = contents;
        this.drafterId = drafterId;
    }
}
