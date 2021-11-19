package playground.domain.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    OPERATING_EXPENSES("운영비"),
    EDUCATION("교육")
    ;

    private final String text;

}
