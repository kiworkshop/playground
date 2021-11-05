package groupware.dto;

import groupware.domain.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DocumentRequest {

    private String title;
    private String category;
    private String contents;
    private long drafterId;
    private List<Long> approverIds;

}
