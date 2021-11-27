package playground.domain.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Category {

    OPERATING_EXPENSES("운영비"),
    EDUCATION("교육"),
    PRODUCT_PURCHASING("물품구매");

    private final String text;

    public static Category findBy(String text) {
        return Arrays.stream(values())
                .filter(category -> category.text.equals(text))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("카테고리 항목이 아닙니다."));
    }
}
