package learning.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    OPERATING_EXPRESS("운영비"),
    EDUCATION("교육"),
    PRODUCT_PURCHASING("물품구매");

    private final String text;
}
