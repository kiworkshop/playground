package playground.domain.document.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.entity.Documents;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class OutBox {

    private final List<BoxDocument> elements;

    public static OutBox of(Documents documents) {
        return OutBox.builder()
                .elements(documents.stream()
                        .map(BoxDocument::of)
                        .collect(Collectors.toList()))
                .build();
    }
}
