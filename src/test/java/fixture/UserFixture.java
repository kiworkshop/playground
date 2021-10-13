package fixture;

import learning.User;

public class UserFixture {

    public static User create(final Long id, final String name) {
        return User.builder()
                .id(id)
                .name(name)
                .build();
    }
}
