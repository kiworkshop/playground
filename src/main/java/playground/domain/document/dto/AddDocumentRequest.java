package playground.domain.document.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class AddDocumentRequest {

    private final String title;
    private final String category;
    private final String contents;
    private final Long drafterId;
    private final List<Long> approverIds;
}
