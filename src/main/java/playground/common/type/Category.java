package playground.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    OPERATING_EXPENSES("운영비"),
    EDUCATION("교육"),
    PRODUCT_PURCHASING("물품구매");

    private final String text;
}