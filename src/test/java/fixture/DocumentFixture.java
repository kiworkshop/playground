package fixture;

import learning.Category;
import learning.Document;
import learning.User;

public class DocumentFixture {

    public static Document create(final Long id, final String title, final Category category,
                                  final String contents, final User drafter) {
        return Document.builder()
                .id(id)
                .title(title)
                .category(category)
                .contents(contents)
                .drafter(drafter)
                .build();
    }
}
