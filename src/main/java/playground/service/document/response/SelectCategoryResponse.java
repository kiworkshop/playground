package playground.service.document.response;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import playground.domain.document.vo.Category;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectCategoryResponse {

    private Category category;

    public SelectCategoryResponse(final Category category) {
        this.category = category;
    }

    public String getValue() {
        return category.name();
    }

    public String getText() {
        return category.getText();
    }
}
