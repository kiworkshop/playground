package fixture;

import learning.Category;
import learning.Document;
import learning.User;

public class DocumentFixture {

    public static Document create(final Long id, final Category category,
                                  final String contents, final User drafter,
                                  final String title) {
        return Document.builder()
                .id(id)
                .category(category)
                .contents(contents)
                .drafter(drafter)
                .title(title)
                .build();
    }
}
