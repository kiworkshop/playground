package playground.domain.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import playground.domain.type.EnumType;

@Getter
@RequiredArgsConstructor
public enum Category implements EnumType {

    OPERATING_EXPENSES("운영비"),
    EDUCATION("교육"),
    PRODUCT_PURCHASING("물품구매");

    private final String text;

}
