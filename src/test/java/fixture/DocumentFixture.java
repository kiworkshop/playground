package fixture;

import playground.domain.document.Category;
import playground.domain.document.Document;
import playground.domain.user.User;

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
