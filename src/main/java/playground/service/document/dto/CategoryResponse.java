package playground.service.document.dto;

import lombok.Builder;
import lombok.Getter;
import playground.domain.document.Category;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class CategoryResponse {
    private String value;
    private String text;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .value(category.name())
                .text(category.getText())
                .build();

    }

    public static List<CategoryResponse> ofList(List<Category> categories) {
        return categories.stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }
}
