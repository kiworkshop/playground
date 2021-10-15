package playground.dto;

import learning.Document;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {

    private String title;
    private String category;
    private String contents;
    private Long drafterId;

    public static DocumentDto convertFrom(Document document) {
        return DocumentDto.builder()
                .title(document.getTitle())
                .category(document.getCategory().getText())
                .contents(document.getContents())
                .drafterId(document.getDrafter().getId())
                .build();
    }
}
