package playground.web.document.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import playground.domain.document.Category;

@Getter
@NoArgsConstructor
public class DocumentCategoryResponse {

    private String value;
    private String text;

    public DocumentCategoryResponse(Category category) {
        this.value = category.name();
        this.text = category.getText();
    }

}
